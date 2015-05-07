package de.i3mainz.dbutils.event;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import de.i3mainz.dbutils.util.JPAUtil;
import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityTransaction;

public class EventManager {

    //private EntityManager em;
    public EventManager() {
        //em = JPAUtil.createEntityManager();
    }

    public List<Potter> getPotters() {

        EntityManager em = JPAUtil.createEntityManager();
        List result = Collections.EMPTY_LIST;
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            result = em.createQuery("Select e from Potter e").getResultList();

            t.commit();
        } catch (Exception e) {
            if (t != null) {

                t.rollback();
            }
        } finally {


            em.close();

        }

        return result;

    }

    public Potter getPotterByID(int id) {

        EntityManager em = JPAUtil.createEntityManager();
        Potter result = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.find(Potter.class, id);

            t.commit();
        } catch (Exception e) {
            if (t != null) {

                t.rollback();
            }
        } finally {


            em.close();

        }

        return result;

    }

    public Potter getPotterByName(String name) {

        EntityManager em = JPAUtil.createEntityManager();
        Potter result = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = (Potter) em.createQuery("Select p from Potter p where p.name='" + name + "'").getSingleResult();

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();

        }

        return result;

    }

    public List<Fragment> getFragments() {

        EntityManager em = JPAUtil.createEntityManager();
        List result = Collections.EMPTY_LIST;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select e from Fragment e").getResultList();

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public List<Fragment> getFragmentsByFindspotName(String FindspotName) {

        EntityManager em = JPAUtil.createEntityManager();
        List result = Collections.EMPTY_LIST;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select e from Fragment e join e.findspot f WHERE f.name='" + FindspotName + "'").getResultList();

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public List<Potter> getPotterByFindspotName(String FindspotName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();;
        List<Potter> result2 = new ArrayList<Potter>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select e from Fragment e join e.findspot f WHERE f.name='" + FindspotName + "'").getResultList();
            for (Iterator<Fragment> it = result.iterator(); it.hasNext();) {
                Potter potter = it.next().getPotter();
                result2.add(potter);
            }

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result2;

    }

    public List<Findspot> getFindspotsByPotterName(String PotterName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();
        List<Findspot> result2 = new ArrayList<Findspot>();
        List<String> result3 = new ArrayList<String>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select f from Fragment f join f.potter p WHERE p.name='" + PotterName + "'").getResultList();
            for (int i = 0; i < result.size(); i++) {
                Findspot findspot = result.get(i).getFindspot();
                Potter potter = result.get(i).getPotter();
                String findspot2 = String.valueOf(findspot.getId());
                boolean match = false;
                for (int j = 0; j < result3.size(); j++) {
                    String findspot3 = result3.get(j).toString();
                    if (findspot3.equals(findspot2)) {
                        match = true;
                    }
                }
                if (match == false) {
                    result2.add(findspot);
                    result3.add(findspot2);
                }
            }

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result2;

    }

    public List<Findspot> getFindspotsLocationsByFindspotName(String FindspotName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Findspot> result = new ArrayList<Findspot>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select f from Findspot f WHERE f.name LIKE '" + FindspotName + "%'").getResultList();

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();


        }

        return result;

    }

    public List<String> getFindspotsStringByPotterName(String PotterName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();
        List<String> result2 = new ArrayList<String>();
        List<String> result3 = new ArrayList<String>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select f from Fragment f join f.potter p WHERE p.name='" + PotterName + "'").getResultList();
            for (int i = 0; i < result.size(); i++) {
                Findspot findspot = result.get(i).getFindspot();
                Potter potter = result.get(i).getPotter();
                String findspot2 = String.valueOf(findspot.getId());
                boolean match = false;
                for (int j = 0; j < result3.size(); j++) {
                    String findspot3 = result3.get(j).toString();
                    if (findspot3.equals(findspot2)) {
                        match = true;
                    }
                }
                if (match == false) {
                    result2.add(findspot.getName());
                    result3.add(findspot2);
                }
            }


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result2;

    }

    public List<String> getPotterStringByFindspotName(String FindspotName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();
        List<String> result2 = new ArrayList<String>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select e from Fragment e join e.findspot f WHERE f.name='" + FindspotName + "'").getResultList();
            for (Iterator<Fragment> it = result.iterator(); it.hasNext();) {
                Potter potter = it.next().getPotter();
                String potter2 = potter.getName().toString();
                boolean match = false;
                for (int j = 0; j < result2.size(); j++) {
                    if (result2.get(j).toString() == potter2) {
                        match = true;
                    }
                }
                if (match == false) {
                    result2.add(potter2);
                }
            }


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();

        }

        return result2;

    }

    public List<String> getFragmentStringByPotterName(String PotterName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();
        List<String> result2 = new ArrayList<String>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select f from Fragment f join f.potter p WHERE p.name='" + PotterName + "'").getResultList();
            for (Iterator<Fragment> it = result.iterator(); it.hasNext();) {
                result2.add(String.valueOf(it.next().getId()));
            }


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();

        }

        return result2;

    }

    public String getPotterStringByFragmentID(String FragmentID) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();
        String result2 = "";
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select f from Fragment f WHERE f.id='" + FragmentID + "'").getResultList();
            for (Iterator<Fragment> it = result.iterator(); it.hasNext();) {
                Potter potter = it.next().getPotter();
                result2 = potter.getName().toString();
            }


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result2;

    }

    public List<String> getFragmentStringByFindspotName(String FindspotName) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result = new ArrayList<Fragment>();
        List<String> result2 = new ArrayList<String>();
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select f from Fragment f join f.findspot p WHERE p.name='" + FindspotName + "'").getResultList();
            for (Iterator<Fragment> it = result.iterator(); it.hasNext();) {
                result2.add(String.valueOf(it.next().getId()));
            }


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result2;

    }

    public Fragment getFragmentByID(int id) {

        EntityManager em = JPAUtil.createEntityManager();
        Fragment result = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.find(Fragment.class, id);


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public List<Findspot> getFindspots() {

        EntityManager em = JPAUtil.createEntityManager();
        List result = Collections.EMPTY_LIST;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select e from Findspot e").getResultList();


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public Findspot getFindspotByName(String name) {

        EntityManager em = JPAUtil.createEntityManager();
        Findspot result = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = (Findspot) em.createQuery("SELECT s FROM Findspot s WHERE s.name='" + name + "'").getSingleResult();


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public Findspot getFindspotByID(int id) {

        EntityManager em = JPAUtil.createEntityManager();
        Findspot result = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.find(Findspot.class, id);


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public Pleiadesmatch getPleiadesIDByName(String name) {

        EntityManager em = JPAUtil.createEntityManager();
        Pleiadesmatch result = null;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = (Pleiadesmatch) em.createQuery("Select p from Pleiadesmatch p where p.findspot='" + name + "'").getSingleResult();


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    public List<Pleiadesmatch> getPleiadesmatch() {

        EntityManager em = JPAUtil.createEntityManager();
        List result = Collections.EMPTY_LIST;
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            result = em.createQuery("Select p from Pleiadesmatch p").getResultList();


            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {


            em.close();
        }

        return result;

    }

    /**
     * Get Fragment IDs and Potter by PotterList
     *
     * @param potters
     * @return
     */
    public Map<String, List<String>> getFragmentIDsByPotterList(List<String> potters) {

        EntityManager em = JPAUtil.createEntityManager();
        List<Fragment> result;
        Map<String, List<String>> result2 = new HashMap<String, List<String>>();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            for (String potter : potters) {
                result = em.createQuery("Select f from Fragment f join f.potter p WHERE p.name='" + potter + "' ORDER BY p.name ASC").getResultList();
                List<String> tmp = new ArrayList<String>();
                for (Iterator<Fragment> it = result.iterator(); it.hasNext();) {
                    tmp.add(String.valueOf(it.next().getId()));
                }
                result2.put(potter, tmp);
            }
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            em.close();
        }

        return result2;
    }

    /**
     * Get Potter Name by FragmentList
     *
     * @param potters
     * @return
     */
    public Map<String, String> getPotterNamesByFragmentList(List<String> fragments) {

        EntityManager em = JPAUtil.createEntityManager();
        Fragment result;
        Map<String, String> result2 = new HashMap<String, String>();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            for (String fragment : fragments) {
                result = (Fragment) em.createQuery("Select f from Fragment f WHERE f.id='" + fragment + "' ORDER BY f.id ASC").getSingleResult();
                String potterName = result.getPotter().getName().toString();
                potterName = potterName.replace(" ", "_");
                result2.put(fragment, potterName);
            }
            t.commit();
        } catch (Exception e) {
            System.out.println(e);
            if (t != null) {
                t.rollback();
            }
        } finally {
            em.close();
        }

        return result2;
    }

    /**
     * Get Potter and Findspot Data By Fragment ID
     *
     * @param potters
     * @return
     */
    public Hashtable<String, String> getPotterFindspotDataByFragmentID(int fragmentID) {

        EntityManager em = JPAUtil.createEntityManager();
        Fragment result;
        Hashtable<String, String> result2 = new Hashtable<String, String>();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            result = (Fragment) em.createQuery("Select f from Fragment f WHERE f.id='" + fragmentID + "'").getSingleResult();
            result2.put("fragmentID", String.valueOf(result.getId()));
            result2.put("fragmentDie", String.valueOf(result.getDie()));
            result2.put("fragmentPotform", String.valueOf(result.getPotform()));
            result2.put("fragmentNumber", String.valueOf(result.getNumber()));
            result2.put("potterName", result.getPotter().getName().toString());
            result2.put("findspotName", result.getFindspot().getName().toString());
            result2.put("findspotLocation", result.getFindspot().getLocation().toText());
            result2.put("findspotKilnsite", String.valueOf(result.getFindspot().getKilnsite()));
            result2.put("findspotLat", String.valueOf(result.getFindspot().getLat()));
            result2.put("findspotLon", String.valueOf(result.getFindspot().getLon()));
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        } finally {
            em.close();
        }

        return result2;
    }
}
