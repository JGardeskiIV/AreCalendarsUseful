/** File:   CalendarPanel.java
 *  Created by J. Gardeski on 5/20/15
 *
 *  Last Modified on 5/20/15
 *  NOTES:
 *      -
 */
package edu.exploration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class CalendarPanel extends JPanel {

    /**	An ActionEvent Listener for JButtons representing days in a month
     * 	within a given Calendar. Specifically, these buttons toggle the
     * 	calendar to either the previous or the next month within a given year.
     */
    private class SwitchMonthsListener implements ActionListener {

        /**	On a button click, update the month contained in the Calendar time instance variable,
         * 	update all panels within the CalendarPanel (header & days), and re-pack
         * 	and re-center the parent frame.
         *
         * 	@pre	-	A month toggle has been clicked, representing the desire to
         * 				either move to the previous or the next month in a given year.
         *
         * 	@post	-	The Calendar time captures the desired movement and updates to the previous
         * 				or next month, and all GUI day buttons and header elements
         * 				update to the respective month.
         */
        public void actionPerformed(ActionEvent e) {
			/*	Find the source of the event, and add or subtract a month from the
			 * 	time once the source has been found.
			 */
            String command = e.getActionCommand();
            if (command.equals(prevMonthButton.getName())) {
                time.add(Calendar.MONTH, -1);
            } else if (command.equals(nextMonthButton.getName())){
                time.add(Calendar.MONTH, 1);
            }
            //	Update all GUI elements within the CalendarPanel.
            updateHeaderPanel();
            updateDaysPanel();

			/*	The LayoutManager in the parent window will make sure changing text within the day buttons
			 * 	remains visible, but we should tell the parent window to re-pack and re-center itself.
			 * 	That means we first need to find the CalendarPanel's ancestor.
			 */
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, CalendarPanel.this);
            parent.pack();
            parent.setLocationRelativeTo(null);
        }
    }

    /***************
     *  Constants  *
     ***************/
    private static final long serialVersionUID = 1L;

    public static final int BORDER_H_GAP = 8;
    public static final int BORDER_V_GAP = 3;
    public static final int FLOW_H_GAP = 15;
    public static final int GRID_H_GAP = 5;
    public static final int GRID_V_GAP = 5;

    public static final int PANEL_PAD = 8;
    public static final int DAYS_PAD = 5;

    public static final int MAX_WEEKS = 6;
    public static final int DAYS_IN_WEEK = 7;
    public static final int DAYS_DISPLAYED = MAX_WEEKS * DAYS_IN_WEEK;

    public static final String[] WEEKDAYS = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };

    /************************
     *  Instance Variables  *
     ************************/
    private JPanel 	headerPanel;
    private JButton prevMonthButton;
    private JLabel  monthYearLabel;
    private JButton nextMonthButton;

    private JPanel daysPanel;

    private Calendar time;

    private JButton[] dayButtons;

    /******************
     *  Constructors  *
     ******************/

    /**	Creates a JPanel with a BorderLayout LayoutManager, with outside padding
     * 	to be seen within its' parent container. Also captures a time instance in
     * 	the passed-in Calendar object, or creates one based off of the current time,
     * 	time zone, and default locale.
     *
     * 	@param aTime	-	a pre-specified time, or the current time if null is passed in.
     */
    public CalendarPanel(Calendar aTime) {
        //	Set the LayoutManager & gap settings.
        super(new BorderLayout(BORDER_H_GAP, BORDER_V_GAP));

        //	If no time is passed in, use the current time; otherwise, create a copy.
        time = (aTime == null) ? Calendar.getInstance() : (Calendar) aTime.clone();

        //	Build the Calendar!
        createHeaderPanel();
        createDaysPanel();

        //	Make it pretty!

        //	Pad the CalendarPanel relative to its' parent container.
        this.setBorder(BorderFactory.createEmptyBorder(	PANEL_PAD, PANEL_PAD, PANEL_PAD, PANEL_PAD));

        this.setBackground(Color.WHITE);

        daysPanel.setBorder(BorderFactory.createEmptyBorder( DAYS_PAD, DAYS_PAD, DAYS_PAD, DAYS_PAD ));
        daysPanel.setBackground(new Color(218, 218, 242));

        headerPanel.setBackground(new Color(136, 136, 154));
        //	customize();
    }

    /**	The default constructor simply calls the above constructor using a Calendar
     * 	instance containing the time of the system at runtime.
     */
    public CalendarPanel() {
        this(Calendar.getInstance());
    }

    /********************
     *  Helper Methods  *
     ********************/

    /**	Initializes the headerPanel and all of its components, calls updateHeaderPanel(),
     * 	and adds the headerPanel to the CalendarPanel.
     *
     * 	@pre	-	The headerPanel does not yet exist.
     * 	@post	-	All headerPanel components are updated, active, and visible.
     */
    private void createHeaderPanel() {
        //	Instantiate the headerPanel with appropriate padding.
        headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, FLOW_H_GAP, BORDER_V_GAP));

		/*	Create the button to allow toggle to the PREVIOUS month, set its' name,
		 * 	add an anonymous SwitchMonthsListener listener, and add the button to the header.
		 */
        prevMonthButton = new JButton("PREV");
        prevMonthButton.setName("PREV");
        prevMonthButton.addActionListener(new SwitchMonthsListener());
        headerPanel.add(prevMonthButton);

		/*	Create the label to echo the current month and year to the screen, add it to
		 * 	the header, and then update the label and currentMonth instance variables.
		 */
        monthYearLabel = new JLabel();
		/*monthYearLabel.setBackground(Color.DARK_GRAY);
		monthYearLabel.setForeground(Color.WHITE);
		monthYearLabel.setOpaque(true);*/
        monthYearLabel.setFont(new Font(null, Font.BOLD, 13));
        monthYearLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)));
        //monthYearLabel.setBackground(Color.WHITE);
        //monthYearLabel.setOpaque(true);
        headerPanel.add(monthYearLabel);
        updateHeaderPanel();

		/*	Create the button to allow toggle to the NEXT month, set its' name,
		 * 	add an anonymous SwitchMonthsListener listener, and add the button to the header.
		 */
        nextMonthButton = new JButton("NEXT");
        nextMonthButton.setName("NEXT");
        nextMonthButton.addActionListener(new SwitchMonthsListener());
        headerPanel.add(nextMonthButton);

        //	Add the header to the top of the CalendarPanel.
        this.add(headerPanel, BorderLayout.NORTH);
    }

    /**	Updates the calendar's month and year display in the header at the top of the panel.
     *
     * 	@pre	-	createHeaderPanel() has already been called, meaning that the
     * 				monthAndYear label has already been instantiated.
     * 				Also, Calendar time represents the updated time data.
     *
     * 	@post	-	The currentMonth instance variable and monthAndYear label
     * 				hold and display updated time values.
     */
    private void updateHeaderPanel() {
        //	Grab the full month name as a String from the time object.
        String currentMonth = time.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale());
        //	Display this in conjunction with the grabbed year.
        monthYearLabel.setText(currentMonth + " " + time.get(Calendar.YEAR));
    }

    /**	Initializes the daysPanel and all of its components, calls updateDaysPanel(),
     * 	and adds the daysPanel to the CalendarPanel.
     *
     * 	@pre	-	The daysPanel does not yet exist.
     * 	@post	-	All daysPanel components are updated, active, and visible.
     */
    private void createDaysPanel() {
		/*	Instantiate the daysPanel - with appropriate padding - to have a header row
		 * 	for the days of the week, 6 additional rows for the days to be displayed,
		 * 	and 7 columns (1 per day).
		 */
        daysPanel = new JPanel(new GridLayout(MAX_WEEKS + 1, DAYS_IN_WEEK, GRID_H_GAP, GRID_V_GAP));
        JLabel[] weekdayLabels = new JLabel[WEEKDAYS.length];

        //  Give it some padding within the CalendarPanel

        //	Build the day header row showing the days of the week.
        for (int i = 0; i < weekdayLabels.length; i++) {
            weekdayLabels[i] = new JLabel(WEEKDAYS[i], JLabel.CENTER);
            weekdayLabels[i].setBackground(Color.darkGray);
            weekdayLabels[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            daysPanel.add(weekdayLabels[i]);
        }

        //	Create all of the day buttons in the grid.
        createDayButtons();

		/*	Update all of the day buttons in the grid
		 * 	to reflect appropriate data.
		 */
        updateDaysPanel();

        //	Add the daysPanel to the center portion of the CalendarPanel
        this.add(daysPanel);
    }

    /**	Updates all day buttons on the daysPanel (in the dayButtons array) to reflect
     * 	changes to the Calendar time instance variable. This includes the value and state
     * 	for the current, previous, and next months with respect to the time.
     *
     * 	@pre	-	createDaysPanel() has already been called, meaning that the
     * 				day buttons and the dayButtons array have been created.
     * 				Also, Calendar time represents the updated time data.
     *
     * 	@post	-	The entire daysPanel reflects the days of the current, previous, and
     * 				next months of the month and year of the Calendar time.
     */
    private void updateDaysPanel() {

        //	First, grab the number of days in the current month.
        int numDays = time.getActualMaximum(Calendar.DAY_OF_MONTH);

		/*	Then, save the value of the current day, determine the weekday of
		 * 	the first day of the given month, and then restore the Calendar
		 * 	time instance variable to its' proper state.
		 */
        int currentDay = time.get(Calendar.DAY_OF_MONTH);

        time.set(Calendar.DAY_OF_MONTH, 1);

        //	Grab the first day of the month (as an int)
        int firstDay = time.get(Calendar.DAY_OF_WEEK);

        //	Restore the calendar's state.
        time.set(Calendar.DAY_OF_MONTH, currentDay);

		/*	If the first day of the month is Sunday or Monday, start the
		 * 	current month in the second row of the grid.
		 */
        if (firstDay < 2) {
            firstDay = firstDay + 7;
        }

		/*	All days before the first day of the current month represent
		 * 	days from the previous month.
		 */
        int dayIndex = 0;
        Calendar temp = (Calendar) time.clone();
        temp.add(Calendar.MONTH, -1);
        int prevDays = temp.getActualMaximum(Calendar.DAY_OF_MONTH) - firstDay + 1;
        do {
            dayButtons[dayIndex].setText(++prevDays + "");
            dayButtons[dayIndex].setEnabled(false);
            dayButtons[dayIndex].setSelected(false);
        } while ( ++dayIndex < firstDay - 1 );

		/*	Update the display for all days in the current month.
		 * 	-	j is the display counter
		 * 	-	dayIndex holds the appropriate dayButtons[] index.
		 */
        int i = 1;
        do {
            dayButtons[dayIndex].setText("" + i);
            dayButtons[dayIndex].setEnabled(true);
            dayButtons[dayIndex++].setSelected(false);
        } while ( ++i <= numDays );

		/*	All days after the end of the current month represent days from
		 * 	the following month.
		 */
        int daysLeft = DAYS_DISPLAYED - numDays - firstDay;
        i = 1;
        do {
            dayButtons[dayIndex].setText(i + "");
            dayButtons[dayIndex].setEnabled(false);
            dayButtons[dayIndex++].setSelected(false);
        } while ( i++ <= daysLeft );

        dayButtons[time.get(Calendar.DAY_OF_MONTH) + firstDay - 2].setSelected(true);
    }

    /**	Creates all of the day buttons to be used in the daysPanel:
     * 	-	adds an action listener to each button, sets its initial state to
     * 		disabled, and adds it to the daysPanel.
     *
     * 	@pre	-	the dayButtons array has not been instantiated, and no buttons exist yet.
     *
     * 	@post	-	DAYS_DISPLAYED buttons have been created, paired with an appropriate
     * 				action listener, disabled at start, and added to the daysPanel.
     */
    private void createDayButtons() {
        //	Create the array.
        dayButtons = new JButton[DAYS_DISPLAYED];
        for (int i = 0; i < DAYS_DISPLAYED; i++) {
            //	Create the button first.
            dayButtons[i] = new JButton();

            //	Add the action listener here.


            //	Buttons are initially disabled.
            dayButtons[i].setEnabled(false);

            //	Add each button to the daysPanel.
            daysPanel.add(dayButtons[i]);
        }
    }
}
