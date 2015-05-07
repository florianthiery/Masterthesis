package de.i3mainz.dbutils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import de.i3mainz.dbutils.event.EventManager;
import de.i3mainz.dbutils.util.JPAUtil;
import java.util.Date;
import javax.persistence.EntityManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
//       EventManager mgr = new EventManager();
//
//        //String wkt = "POINT(10 45)";
//        //mgr.createAndStoreEvent("My Event", new Date(), wkt );
//
//        System.out.println(mgr.getEvents().get(0).getLocation().toText());
//        System.out.println(mgr.getEvent(new Long(4)).getTitle());
//        System.out.println(mgr.getPotters().get(0).getName());
        
        //System.out.println(mgr.getFragments().get(0));
       
//       System.out.println(mgr.getFindspots().get(0));
//        
//        JPAUtil.close();
    }
}
