package edu.exploration;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Container;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                /*try {
                    //  Set the System Look & Feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (UnsupportedLookAndFeelException e) {
                    //  Handle Exception?
                }
                catch (ClassNotFoundException e) {
                    //  Handle Exception?
                }
                catch (InstantiationException e) {
                    //  Handle Exception?
                }
                catch (IllegalAccessException e) {
                    //  Handle Exception?
                }*/

                //  Instantiate a Calendar!
                JFrame window = new JFrame("SwingCalendar");

                Container contentPane = window.getContentPane();
                contentPane.setLayout(new BorderLayout(5, 5));

                CalendarPanel calendar = new CalendarPanel();
                contentPane.add(calendar);

                //  Dispose when closed.
                window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //  Pack it up!
                window.pack();

                //  Center the window on the screen.
                window.setLocationRelativeTo(null);

                //  Now show the damn thing.
                window.setVisible(true);
            }
        });
    }
}
