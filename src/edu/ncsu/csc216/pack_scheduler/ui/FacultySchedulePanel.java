package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Creates a User interface for working with the FacultySchedule
 * 
 * @author kmbrown
 *
 */
public class FacultySchedulePanel extends JPanel {
	/** ID used for object serialization */
	private static final long serialVersionUID = 1L;

	private JPanel facultyPanel;

	private JTable tableSchedule;
	private JTable tableRoll;

	private CourseTableModel scheduleTableModel;

	private StudentTableModel rollTableModel;

	private Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

	private JScrollPane scrollSchedule;
	private JScrollPane scrollRoll;

	private JPanel pnlCourseDetails;

	private JLabel lblNameTitle = new JLabel("Name: ");
	private JLabel lblSectionTitle = new JLabel("Section: ");
	private JLabel lblTitleTitle = new JLabel("Title: ");
	private JLabel lblInstructorTitle = new JLabel("Instructor: ");
	private JLabel lblCreditsTitle = new JLabel("Credits: ");
	private JLabel lblMeetingTitle = new JLabel("Meeting: ");
	private JLabel lblEnrollmentCapTitle = new JLabel("Enrollment Cap: ");
	private JLabel lblOpenSeatsTitle = new JLabel("Open Seats: ");
	private JLabel lblWaitlistTitle = new JLabel("Waitlist: ");

	private JLabel lblName = new JLabel("");
	private JLabel lblSection = new JLabel("");
	private JLabel lblTitle = new JLabel("");
	private JLabel lblInstructor = new JLabel("");
	private JLabel lblCredits = new JLabel("");
	private JLabel lblMeeting = new JLabel("");
	private JLabel lblEnrollmentCap = new JLabel("");
	private JLabel lblOpenSeats = new JLabel("");
	private JLabel lblWaitlist = new JLabel("");

	private Faculty currentUser;
	private FacultySchedule schedule;
	private CourseRoll courseRoll;

	/**
	 * Constructs a new Faculty Schedule Panel
	 */
	public FacultySchedulePanel() {
		super(new FlowLayout());

		RegistrationManager manager = RegistrationManager.getInstance();
		currentUser = (Faculty) manager.getCurrentUser();

		// Set up main Panel
		facultyPanel = new JPanel(new BorderLayout());

		// Set up Course Details Panel
		pnlCourseDetails = new JPanel(new GridLayout(9, 1));

		// Name
		JPanel pnlName = new JPanel(new GridLayout(1, 2));
		pnlName.add(lblNameTitle);
		pnlName.add(lblName);

		// Title
		JPanel pnlTitle = new JPanel(new GridLayout(1, 2));
		pnlTitle.add(lblTitleTitle);
		pnlTitle.add(lblTitle);

		// Section
		JPanel pnlSection = new JPanel(new GridLayout(1, 2));
		pnlSection.add(lblSectionTitle);
		pnlSection.add(lblSection);

		// Credits
		JPanel pnlCredits = new JPanel(new GridLayout(1, 2));
		pnlCredits.add(lblCreditsTitle);
		pnlCredits.add(lblCredits);

		// Instructor
		JPanel pnlInstructor = new JPanel(new GridLayout(1, 2));
		pnlInstructor.add(lblInstructorTitle);
		pnlInstructor.add(lblInstructor);

		// Meeting
		JPanel pnlMeeting = new JPanel(new GridLayout(1, 2));
		pnlMeeting.add(lblMeetingTitle);
		pnlMeeting.add(lblMeeting);

		// Enrollment Cap
		JPanel pnlEnroll = new JPanel(new GridLayout(1, 2));
		pnlEnroll.add(lblEnrollmentCapTitle);
		pnlEnroll.add(lblEnrollmentCap);

		// Waitlist
		JPanel pnlWait = new JPanel(new GridLayout(1, 2));
		pnlWait.add(lblWaitlistTitle);
		pnlWait.add(lblWaitlist);

		// Open Seats
		JPanel pnlOpen = new JPanel(new GridLayout(1, 2));
		pnlOpen.add(lblOpenSeatsTitle);
		pnlOpen.add(lblOpenSeats);

		pnlCourseDetails.add(pnlName);
		pnlCourseDetails.add(pnlSection);
		pnlCourseDetails.add(pnlTitle);
		pnlCourseDetails.add(pnlCredits);
		pnlCourseDetails.add(pnlInstructor);
		pnlCourseDetails.add(pnlMeeting);
		pnlCourseDetails.add(pnlEnroll);
		pnlCourseDetails.add(pnlOpen);
		pnlCourseDetails.add(pnlWait);

		TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
		pnlCourseDetails.setBorder(borderCourseDetails);
		pnlCourseDetails.setToolTipText("Course Details");

		// Set up Faculty Schedule Panel
		scheduleTableModel = new CourseTableModel();
		tableSchedule = new JTable(scheduleTableModel) {
			private static final long serialVersionUID = 1L;

			/**
			 * Set custom tool tips for cells
			 * 
			 * @param e
			 *            MouseEvent that causes the tool tip
			 * @return tool tip text
			 */
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);

				return (String) scheduleTableModel.getValueAt(rowIndex, realColumnIndex);
			}
		};

		tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 200));
		tableSchedule.setFillsViewportHeight(true);
		tableSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String name = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString();
				String section = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 1).toString();
				CourseCatalog catalog = RegistrationManager.getInstance().getCourseCatalog();
				Course c = catalog.getCourseFromCatalog(name, section);
				updateCourseDetails(c);
				courseRoll = c.getCourseRoll();
				rollTableModel.updateData();
				
			}

		});

		scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel facSchedulePanel = new JPanel(new FlowLayout());

		TitledBorder borderFacultySchedule = BorderFactory.createTitledBorder(lowerEtched, "Faculty Schedule");
		facSchedulePanel.setBorder(borderFacultySchedule);
		facSchedulePanel.setToolTipText("Faculty Schedule");
		facSchedulePanel.add(scrollSchedule);

		// Set up Course Roll Panel
		rollTableModel = new StudentTableModel();
		tableRoll = new JTable(rollTableModel);

		tableRoll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRoll.setPreferredScrollableViewportSize(new Dimension(500, 200));
		tableRoll.setFillsViewportHeight(true);

		scrollRoll = new JScrollPane(tableRoll, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		JPanel courseRollPanel = new JPanel(new FlowLayout());

		TitledBorder borderCourseRoll = BorderFactory.createTitledBorder(lowerEtched, "Course Roll");
		courseRollPanel.setBorder(borderCourseRoll);
		courseRollPanel.setToolTipText("Course Roll");
		courseRollPanel.add(scrollRoll);

		updateTables();

		// Add panels to Main Panel
		facultyPanel.add(facSchedulePanel, BorderLayout.NORTH);
		facultyPanel.add(pnlCourseDetails, BorderLayout.CENTER);
		facultyPanel.add(courseRollPanel, BorderLayout.SOUTH);

		// make Main Panel scrollable
		JScrollPane scrollMain = new JScrollPane(facultyPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// add Main Panel
		this.add(scrollMain);

	}

	/**
	 * Updates the pnlCourseDetails with full information about the most
	 * recently selected course.
	 * 
	 * @param c
	 *            the Course to update
	 */
	private void updateCourseDetails(Course c) {
		if (c != null) {
			lblName.setText(c.getName());
			lblSection.setText(c.getSection());
			lblTitle.setText(c.getTitle());
			lblInstructor.setText(c.getInstructorId());
			lblCredits.setText("" + c.getCredits());
			lblMeeting.setText(c.getMeetingString());
			lblEnrollmentCap.setText("" + c.getCourseRoll().getEnrollmentCap());
			lblOpenSeats.setText("" + c.getCourseRoll().getOpenSeats());
		} else if (c == null){
			lblName.setText("");
			lblSection.setText("");
			lblTitle.setText("");
			lblInstructor.setText("");
			lblCredits.setText("");
			lblMeeting.setText("");
			lblEnrollmentCap.setText("");
			lblOpenSeats.setText("");
		}
	}

	/**
	 * Underlies the Faculty Schedule JTable object
	 * 
	 * @author kmbrown
	 *
	 */
	private class CourseTableModel extends AbstractTableModel {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String[] columnNames = { "Name", "Section", "Title", "Meeting Days", "Open Seats" };
		/** Data stored in the table */
		private Object[][] data;

		public CourseTableModel() {
			updateData();
		}

		/**
		 * Returns the number of columns in the table.
		 * 
		 * @return the number of columns in the table.
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * 
		 * @return the number of rows in the table.
		 */
		public int getRowCount() {
			if (data == null)
				return 0;
			return data.length;
		}

		/**
		 * Returns the column name at the given index.
		 * 
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * 
		 * @return the data at the given location.
		 */
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}

		/**
		 * Updates the model with Course Information from the FacultySchedule
		 */
		private void updateData() {
			currentUser = (Faculty) RegistrationManager.getInstance().getCurrentUser();
			if (currentUser != null) {
				schedule = currentUser.getSchedule();
				data = schedule.getScheduledCourses();

				FacultySchedulePanel.this.repaint();
				FacultySchedulePanel.this.validate();
			}
		}

	}

	/**
	 * StudentTableModel is the object that underlies the JTable that displays
	 * the Students on the CourseRoll to the user
	 * 
	 * @author Sarah Heckman
	 * @author kmbrown
	 *
	 */
	private class StudentTableModel extends AbstractTableModel {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names for the table */
		private String[] columnNames = { "First Name", "Last Name", "Student ID" };
		/** Data stored in the table */
		private Object[][] data;

		/**
		 * Constructs the StudentTableModel by requesting the latest information
		 * from the RegistrationManager
		 */
		public StudentTableModel() {
			updateData();
		}

		/**
		 * Returns the number of columns in the table.
		 * 
		 * @return the number of columns in the table.
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * 
		 * @return the number of rows in the table.
		 */
		public int getRowCount() {
			if (data == null)
				return 0;
			return data.length;
		}

		/**
		 * Returns the column name at the given index.
		 * 
		 * @param col
		 *            the column
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given {row, col} index.
		 * 
		 * @param row
		 *            the row location
		 * @param col
		 *            the column location
		 * @return the data at the given location.
		 */
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}

		/**
		 * Sets the given value to the given {row, col} location.
		 * 
		 * @param value
		 *            Object to modify in the data.
		 * @param row
		 *            location to modify the data.
		 * @param col
		 *            location to modify the data.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		/**
		 * Updates the given model with Student information from the CourseRoll.
		 */
		public void updateData() {
			if (courseRoll != null) {
				data = courseRoll.getStudentsOnRoll();
			} else if (courseRoll == null){
				data = null;
				updateCourseDetails(null);
			}
			
			FacultySchedulePanel.this.repaint();
			FacultySchedulePanel.this.validate();
		}

	}

	/**
	 * Updates the FacultySchedule and CourseRoll tables.
	 */
	public void updateTables() {
		scheduleTableModel.updateData();
		rollTableModel.updateData();
	}
}