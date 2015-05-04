
/**
 * This program implements the BIRCH algorithm and prints the CF Tree for the
 * given dataset.
 *
 * @author Daniel Swain Jr
 *
 */
public class BIRCH {

  /**
   * Cluster Feature
   */
  class CF {
    int radius; // Radius

    /**
     * @return -> The number of objects in a cluster. 
     */
    int get_n() {
      return 0;
    }

    /**
     * @return -> The sum of the attributes of the objects inside of the cluster. 
     */
    int[] get_LS() {
      return new int[0];
    }

    /**
     * @return -> The square sum of the attributes of the objects inside of the cluster. 
     */
    int get_SS() {
      return 0;
    }
  }

  /**
   * Cluster Feature Tree
   */
  class CFTree {
    int B; // Branch factor
    int T; // Threshold

  }
}
