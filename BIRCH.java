
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
  class ClusterFeature {

    private ClusterFeature parent;
    private final ArrayList<int[]> children = new ArrayList<>();
    private int b; // Max Number of Children

    ClusterFeature() {

    }

    ClusterFeature(int b) {
      this.b = b;
    }

    /**
     * Attempts to add a record to the CF node.
     *
     * @param record -> Record to add.
     * @return -> true if Record can be added, false if not.
     */
    boolean add(int[] record) {
      return true;
    }

    /**
     * Sets the parent of the CF node;
     *
     * @param parent -> Parent to set to.
     */
    void set_parent(ClusterFeature parent) {
      this.parent = parent;
    }

    ClusterFeature get_parent() {
      return parent;
    }

    ArrayList get_children() {
      return children;
    }

    int get_b() {
      return b;
    }

    /**
     * @return -> The radius of the CF.
     */
    double[] get_r() {
      double[] radius = new double[7];
      int n = get_n();
      int[] ls = get_LS();
      int[] ss = get_SS();
      for (int i = 0; i < radius.length; i++) {
//        //Test Variables
//        // n * SS
//        double nSS = n * ss[i];
//        // 2LS * 2LS
//        double lsls = 2 * (ls[i] * ls[i]);
//        // nLS
//        double nLS = n * ls[i];
//        // n * n
//        double nSquared = n * n;
//        // [ nSS - 2(LS * LS) + nLS ] / (n * n)
//        double frac = (nSS - lsls + nLS)/ nSquared;
//        // sqRt(frac)
//        double sqRt = Math.sqrt(frac);
        radius[i] = Math.sqrt(((n * ss[i]) - (2 * (ls[i] * ls[i])) + (n * ls[i])) / (n * n));
      }

      return radius;
    }

    /**
     * @return -> The number of objects in a cluster.
     */
    int get_n() {
      return children.size();
    }

    /**
     * @return -> The sum of the attributes of the objects inside of the
     * cluster.
     */
    int[] get_LS() {
      int[] sum = new int[7];
      children.stream().forEach((child) -> {
        for (int i = 0; i < sum.length; i++) {
          sum[i] += child[i];
        }
      });
      return sum;
    }

    /**
     * @return -> The square sum of the attributes of the objects inside of the
     * cluster.
     */
    int[] get_SS() {
      int[] sum = new int[7];
      children.stream().forEach((child) -> {
        for (int i = 0; i < sum.length; i++) {
          sum[i] += child[i] * child[i];
        }
      });
      return sum;
    }
  }

  /**
   * Cluster Feature Tree
   */
  class CFTree {

    int B; // Branch factor
    int T; // Threshold

    void insert() {

    }
  }

  /**
   * Runs the BIRCH algorithm with...
   *
   * @param branch_factor -> The maximum number of leaves/branches for a node of
   * the tree.
   * @param threshold -> The maximum diameter for a node of the tree.
   */
  public void run(int branch_factor, int threshold) {
    File data_file = new File("dataset.csv");
    ArrayList<int[]> data_set = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(data_file))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (!line.startsWith("N")) {
          int[] record = new int[7];
          String[] s = line.split(",");

          if (s.length == 0) {
            break;
          }

          for (int i = 0; i < s.length; i++) {
            record[i] = Integer.parseInt(s[i]);
          }

          data_set.add(record);
        }
      }
    } catch (IOException ex) {
      System.out.println("Error: File(" + data_file.getName() + ") could not be read. ");
    }
  }
}
