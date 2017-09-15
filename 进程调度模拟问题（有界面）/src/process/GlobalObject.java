package process;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GlobalObject {

    /**
     * 全局jScrollPane
     */
    public static JScrollPane jScrollPane;
    public static JScrollPane jScrollPane2;
    public static JScrollPane getjScrollPane() {
        return jScrollPane;
    }
    public static void setjScrollPane(JScrollPane jScrollPane) {
        GlobalObject.jScrollPane = jScrollPane;
    }
    
    public static JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }
    public static void setjScrollPane2(JScrollPane jScrollPane2) {
        GlobalObject.jScrollPane2 = jScrollPane2;
    }


    /**
     * 全局JTable
     */
    public static JTable jTable;
    public static JTable jTable2;
    public static JTable getjTable() {
        return jTable;
    }
    public static void setjTable(JTable jTable) {
        GlobalObject.jTable = jTable;
    }
    
    public static JTable getjTable2() {
        return jTable2;
    }
    public static void setjTable2(JTable jTable2) {
        GlobalObject.jTable2 = jTable2;
    }
}