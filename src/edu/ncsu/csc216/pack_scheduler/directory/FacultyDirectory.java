package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Maintains a directory of all Faculty, identifiable by unique id
 * @author kmbrown
 *
 */
public class FacultyDirectory {
	/**List of Faculty in the Directory*/
	private ArrayList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Creates an empty Faculty directory.
	 */
	public FacultyDirectory(){
		newFacultyDirectory();
	}
	
	/**
	 * Creates an empty Faculty directory.  All Faculty in previous list will be lost unless previously saved by user.
	 */
	public void newFacultyDirectory(){
		facultyDirectory = new ArrayList<Faculty>();
	}

	/**
	 * Constructs a Faculty directory by reading in Faculty records from a given file.  Throws an IllegalArgumentException if the file cannot be found or read.
	 * @param fileName the file containing the Faculty records
	 */
	public void loadFacultyFromFile(String fileName){
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * Adds a new Faculty member to the directory.  Returns true if the Faculty member is added to the Directory and false if the Faculty is a duplicate and is unable to be added.
	 * @param firstName Faculty member's first name, cannot be null or empty
	 * @param lastName Faculty member's last name, cannot be null or empty
	 * @param id Faculty member's unique id, cannot be a duplicate
	 * @param email Faculty member's email
	 * @param password Faculty member's password, cannot be null or empty
	 * @param repeatPassword Faculty member's, cannot be null or empty
	 * @param maxCourses Maximum number of Course's the Faculty member can teach in a semester, between 1 and 3, inclusive
	 * @return true if the Faculty member has been added to the FacultyDirectory
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses){
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		// If an IllegalArgumentException is thrown, it's passed up from Faculty
		// to the GUI
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if (f.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}
	
	/**
	 * Removes Faculty member from Directory, if present
	 * @param id the unique id of the Faculty member to be removed
	 * @return true if the Faculty member was removed
	 */
	public boolean removeFaculty(String id){
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if(f.getId().equals(id)){
				facultyDirectory.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns all Faculty in the directory with a column for first name, last name, and id.
	 * @return String array containing Faculty first name, last name, and id
	 */
	public String[][] getFacultyDirectory(){
		
		String [][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			
			User f = facultyDirectory.get(i);
			directory[i][0] = f.getFirstName();
			directory[i][1] = f.getLastName();
			directory[i][2] = f.getId();
			
		}
		
		return directory;
	}
	
	/**
	 * Saves all the Faculty in the directory to a given file
	 * @param fileName the name of the file in which to save the Faculty list
	 * @throws IllegalArgumentException if unable to write to file
	 */
	public void saveFacultyDirectory(String fileName){
		
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e){
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
	
	/**
	 * Retrieves the Faculty from the FacultyDirectory by searching for the presence of the Faculty id in the directory
	 * @param id the id belonging to the Faculty member to retrieve
	 * @return the Faculty with matching id
	 */
	public Faculty getFacultyById(String id){
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			if(f.getId().equals(id)) {
				return f;
			}
			
		}
		return null;
	}
}
