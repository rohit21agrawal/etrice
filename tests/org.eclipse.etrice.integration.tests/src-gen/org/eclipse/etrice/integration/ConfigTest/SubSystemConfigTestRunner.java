/**
 * @author generated by eTrice
 *
 * this class contains the main function running component SubSystemConfigTest
 * it instantiates SubSystemConfigTest and starts and ends the lifecycle
 */

package org.eclipse.etrice.integration.ConfigTest;

import org.eclipse.etrice.runtime.java.modelbase.SubSystemRunnerBase;

class SubSystemConfigTestRunner extends SubSystemRunnerBase {

	static SubSystemConfigTest main_component = new SubSystemConfigTest(null, "SubSystemConfigTest");

	/**
     * main function
     * creates component and starts and stops the lifecycle
     */
	public static void main(String[] args) {

		System.out.println("***   T H E   B E G I N   ***");
		
		main_component.init(); // lifecycle init
		main_component.start(); // lifecycle start

		// application runs until quit 
		waitForQuit();
		
		// end the lifecycle
		main_component.stop(); // lifecycle stop
		main_component.destroy(); // lifecycle destroy

		System.out.println("***   T H E   E N D   ***");
	}
};
