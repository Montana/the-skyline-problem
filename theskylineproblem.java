import java.util.*;

public class Skyline {
    public List<int[]> getSkyline(int[][] buildings) {
     
        TreeMap<Integer, List<int[]>> idxes = new TreeMap<>();
        for (int i = 0; i < buildings.length; i++) {
            int[] b = buildings[i];
            if (!idxes.containsKey(b[0])) idxes.put(b[0], new LinkedList<int[]>());
            if (!idxes.containsKey(b[1])) idxes.put(b[1], new LinkedList<int[]>());
            idxes.get(b[0]).add(new int[]{1, i}); 
            idxes.get(b[1]).add(new int[]{0, i}); 
        }
        
        LinkedList<int[]> ans = new LinkedList<int[]>();
       
        PriorityQueue<int[]> bActive = new PriorityQueue<>(1, new Comparator<int[]>(){
            public int compare(int[] a, int[] b) {
               
                return (a[2] > b[2])? -1: (a[2] < b[2])? 1: (a==b)? 0: 1;
            }
        });
        for (Map.Entry<Integer, List<int[]>> entry: idxes.entrySet()) {
            int idx = entry.getKey();
            List<int[]> bs = entry.getValue();
            for (int[] bInfo: bs) {
                if (bInfo[0] == 0) {
                    bActive.remove(buildings[bInfo[1]]);
                } else { // bInfo[0] == 1
                    bActive.add(buildings[bInfo[1]]);
                }
            }
            if (bActive.isEmpty()) { 
                ans.add(new int[]{idx, 0});
            } else {
                int h = bActive.peek()[2];
                if (ans.isEmpty() || ans.getLast()[1] != h) {
                    ans.add(new int[]{idx, h});
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int[][] bs = new int[sc.nextInt()][3];
            for (int i = 0; i < bs.length; i++) {
                bs[i][0] = sc.nextInt();
                bs[i][1] = sc.nextInt();
                bs[i][2] = sc.nextInt();
            }
            Skyline sol = new Skyline();
            List<int[]> ans = sol.getSkyline(bs);
            StringBuilder buf = new StringBuilder("[");
            for (int[] a: ans) {
                buf.append("[" + a[0] + ", " + a[1] + "], ");
            }
            buf.setLength(buf.length() - 2);
            buf.append("]");
            System.out.println(buf);
        }
    }
}
