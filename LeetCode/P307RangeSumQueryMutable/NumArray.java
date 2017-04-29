


public class NumArray {

    private final int[] nums;

    public class Node {
        int sum;
        int from;
        int to;
        Node left;
        Node right;

        public Node(int i, int length) {
            this.from = i;
            this.to = length;
            if (to - from <= 4) {
                for (int j = from; j < to; j++) {
                    sum += nums[j];
                }
            } else {
                int middle = from + (to - from) / 2;
                left = new Node(from, middle);
                right = new Node(middle, to);
                sum += left.sum;
                sum += right.sum;
            }
        }

        public void add(int i, int i1) {
            if (i >= from && i < to) {
                sum += i1;
                if (left != null) left.add(i, i1);
                if (right != null) right.add(i, i1);
            }
        }

        public int sumRange(int i, int j) {
            if (i == from && j == to) {
                return sum;
            } else {
                if (left == null) {
                    int s = 0;
                    for (; i < j; i++) s += nums[i];
                    return s;
                } else {
                    if (j <= left.to) {
                        return left.sumRange(i, j);
                    } else if (i >= right.from) {
                        return right.sumRange(i, j);
                    } else {
                        return left.sumRange(i, left.to) + right.sumRange(right.from, j);
                    }
                }
            }
        }
    }

    public Node root;
    public NumArray(int[] nums) {
        this.nums = nums;
        root = new Node(0, nums.length);
    }

    public void update(int i, int val) {
        root.add(i, val - nums[i]);
        nums[i] = val;
    }

    public int sumRange(int i, int j) {
        return root.sumRange(i, j + 1);
    }
}

