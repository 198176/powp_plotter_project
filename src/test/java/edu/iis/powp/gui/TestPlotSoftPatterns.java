package edu.iis.powp.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.iis.client.plottermagic.ClientPlotter;
import edu.iis.client.plottermagic.IPlotter;
import edu.iis.powp.adapter.DrawPlotterAdapter;
import edu.iis.powp.adapter.LinePlotterAdapter;
import edu.iis.powp.app.Application;
import edu.iis.powp.app.Context;
import edu.iis.powp.app.DriverManager;
import edu.iis.powp.appext.ApplicationWithDrawer;
import edu.iis.powp.events.predefine.SelectTestFigureOptionListener;
import edu.kis.powp.drawer.shape.LineFactory;

public class TestPlotSoftPatterns {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param context
	 *            Application context.
	 */
	private static void setupPresetTests(Context context) {

		context.addTest(SelectTestFigureOptionListener.FIGURE_JOE_1, 
				new SelectTestFigureOptionListener(SelectTestFigureOptionListener.FIGURE_JOE_1));
		context.addTest(SelectTestFigureOptionListener.FIGURE_JOE_2, 
				new SelectTestFigureOptionListener(SelectTestFigureOptionListener.FIGURE_JOE_2));
		context.addTest(SelectTestFigureOptionListener.FIGURE_JANE, 
				new SelectTestFigureOptionListener(SelectTestFigureOptionListener.FIGURE_JANE));
		context.addTest(SelectTestFigureOptionListener.RECTANGLE, 
				new SelectTestFigureOptionListener(SelectTestFigureOptionListener.RECTANGLE));
		context.addTest(SelectTestFigureOptionListener.TRAPEZE, 
				new SelectTestFigureOptionListener(SelectTestFigureOptionListener.TRAPEZE));
	}

	/**
	 * Setup driver manager, and set default IPlotter for application.
	 * 
	 * @param context
	 *            Application context.
	 */
	private static void setupDrivers(Context context) {
		
		IPlotter plotter = new DrawPlotterAdapter();
		context.addDriver("Buggy Simulator", plotter);
		Application.getComponent(DriverManager.class).setCurrentPlotter(plotter);

		IPlotter lineSpecialPlotter = new LinePlotterAdapter(LineFactory.getSpecialLine());
		context.addDriver("Line Special Plotter", lineSpecialPlotter);

		IPlotter lineDottedPlotter = new LinePlotterAdapter(LineFactory.getDottedLine());
		context.addDriver("Line Dotted Plotter", lineDottedPlotter);
		
		IPlotter clientPlotter = new ClientPlotter();
		context.addDriver("Client Plotter", clientPlotter);

		context.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param context
	 *            Application context.
	 */
	// private static void setupDefaultDrawerVisibilityManagement(Context context) {
	// DefaultDrawerFrame defaultDrawerWindow =
	// DefaultDrawerFrame.getDefaultDrawerFrame();
	// context.addComponentMenuElementWithCheckBox(DrawPanelController.class,
	// "Default Drawer Visibility",
	// new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
	// defaultDrawerWindow.setVisible(true);
	// }

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param context
	 *            Application context.
	 */
	private static void setupLogger(Context context) {
		Application.addComponent(Logger.class);
		context.addComponentMenu(Logger.class, "Logger", 0);
		context.addComponentMenuElement(Logger.class, "Clear log", 
				(ActionEvent e) -> context.flushLoggerOutput());
		context.addComponentMenuElement(Logger.class, "Fine level", 
				(ActionEvent e) -> LOGGER.setLevel(Level.FINE));
		context.addComponentMenuElement(Logger.class, "Info level", 
				(ActionEvent e) -> LOGGER.setLevel(Level.INFO));
		context.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> LOGGER.setLevel(Level.WARNING));
		context.addComponentMenuElement(Logger.class, "Severe level", 
				(ActionEvent e) -> LOGGER.setLevel(Level.SEVERE));
		context.addComponentMenuElement(Logger.class, "OFF logging", 
				(ActionEvent e) -> LOGGER.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ApplicationWithDrawer.configureApplication();
				Context context = Application.getComponent(Context.class);

				// setupDefaultDrawerVisibilityManagement(context);

				setupDrivers(context);
				setupPresetTests(context);
				setupLogger(context);
			}

		});
	}

}
