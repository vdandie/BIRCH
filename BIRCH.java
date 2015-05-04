
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
    private ArrayList<ClusterFeature> children = new ArrayList<>();
    private final ArrayList<int[]> records = new ArrayList<>();
    private int b; // Max Number of Children

    ClusterFeature() {

    }

    ClusterFeature(int b) {
      this.b = b;
    }

    /**
     * Attempts to add a record to the CF node.
     * @param record -> Record to add.
     * @return -> true if Record can be added, false if not.
     */
    public void add(int[] record) {
      records.add(record);
    }

    /**
     * Sets the parent of the CF node.
     * @param parent -> Parent to set to.
     */
    public void set_parent(ClusterFeature parent) {
      this.parent = parent;
    }
    
    public void set_children(ArrayList<ClusterFeature> children) {
      this.children = children;
    }
    
    public void add_child(ClusterFeature child) {
      if(children == null) {
        children = new ArrayList<>();
        children.add(child);
      } else {
        children.add(child);
      }
    }
    
    /**
     * Sets the maximum number of branches allowed in the CF.
     * @param b -> Number of branches allowed.
     */
    public void set_b(int b) {
      this.b = b;
    }

    public ClusterFeature get_parent() {
      return parent;
    }
    
    public ArrayList get_children() {
      return children;
    }

    public ArrayList get_records() {
      return records;
    }

    public int get_b() {
      return b;
    }

    /**
     * @return -> The radius of the CF.
     */
    public double[] get_r() {
      double[] radius = new double[7];
      int n = get_n();
      int[] ls = get_LS();
      int[] ss = get_SS();
      if(n == 0) return new double[] {-1};
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
     * @return -> The diameter of the CF. 
     */
    public double[] get_d() {
      double[] diameter = new double[7];
      int n = get_n();
      int[] ls = get_LS();
      int[] ss = get_SS();
      for(int i = 0; i < diameter.length; i++) {
        diameter[i]= Math.sqrt(((2 * ss[i]) - (2 * (ls[i] * ls[i]))) / (n * (n - 1)));
      }
      return diameter;
    }

    /**
     * @return -> The number of objects in a cluster.
     */
    public int get_n() {
      return records.size();
    }

    /**
     * @return -> The sum of the attributes of the objects inside of the
     * cluster.
     */
    public int[] get_LS() {
      int[] sum = new int[7];
      records.stream().forEach((record) -> {
        for (int i = 0; i < sum.length; i++) {
          sum[i] += record[i];
        }
      });
      return sum;
    }

    /**
     * @return -> The square sum of the attributes of the objects inside of the
     * cluster.
     */
    public int[] get_SS() {
      int[] sum = new int[7];
      records.stream().forEach((record) -> {
        for (int i = 0; i < sum.length; i++) {
          sum[i] += record[i] * record[i];
        }
      });
      return sum;
    }
    
    public String to_string() {
      String str = "< " + get_n() + ", ( ";
      for(int ls : get_LS()) {
        str += ls + " ";
      }
      str += "), ( ";
      for(int ss : get_SS()) {
        str += ss + " ";
      }
      str += ") >";
      return str;
    }
  }

  /**
   * Cluster Feature Tree
   */
  class CFTree {
    
    ClusterFeature root;
    int B; // Branch factor
    int T; // Threshold
    
    CFTree() {
      root = null;
    }
    
    public void insert(int[] record) {
      if (root == null) {
        root = new ClusterFeature(B);
        insert(root, record);
      } else {
        insert(root, record);
      }
    }
    
    private void insert(ClusterFeature cf, int[] record) {
      if(cf.children == null && cf.records == null) {
        
      }
    }
  }

  /**
   * Runs the BIRCH algorithm with...
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
