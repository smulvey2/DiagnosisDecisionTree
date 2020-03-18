import java.util.ArrayList;
import java.util.List;

/**
 * Fill in the implementation details of the class DecisionTree using this file. Any methods or
 * secondary classes that you want are fine but we will only interact with those methods in the
 * DecisionTree framework.
 */
public class DecisionTreeImpl {
	public DecTreeNode root;
	public List<List<Integer>> trainData;
	public int maxPerLeaf;
	public int maxDepth;
	public int numAttr;
	public int currDepth = 0;

	// Build a decision tree given a training set
	DecisionTreeImpl(List<List<Integer>> trainDataSet, int mPerLeaf, int mDepth) {
		this.trainData = trainDataSet;
		this.maxPerLeaf = mPerLeaf;
		this.maxDepth = mDepth;
		if (this.trainData.size() > 0) this.numAttr = trainDataSet.get(0).size() - 1;
		this.root = buildTree(0, trainData);
	}
	public double classLabel(List<List<Integer>> data) {
		double yes = 0;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).get(data.get(i).size() - 1) == 1) {
				yes ++;
			}
		}
		return yes;
	}
	private DecTreeNode buildTree(int depth, List<List<Integer>> data) {
		
		// TODO: add code here
		if (data.size() == 0) {
			DecTreeNode node = new DecTreeNode(1, 0, 0);
			return node;
		}
		
		
		if (classLabel(data) == data.size()) {
			//System.out.println("hits");
			DecTreeNode node = new DecTreeNode(1, 0, 0);
			return node;
		}
		if (classLabel(data) == 0) {
			DecTreeNode node = new DecTreeNode(0, 0, 0);
			return node;
		}
		
		
		if (depth == maxDepth) {
			if (classLabel(data)/data.size() >= 0.5) {
				DecTreeNode node = new DecTreeNode(1, 0, 0);
				return node;
			}
			else {
				DecTreeNode node = new DecTreeNode(0, 0, 0);
				return node;
			}
		}
		if (data.size() < maxPerLeaf) {
			if (classLabel(data)/data.size() >= 0.5) {
				DecTreeNode node = new DecTreeNode(1, 0, 0);
				return node;
			}
			else {
				DecTreeNode node = new DecTreeNode(0, 0, 0);
				return node;
			}
		}
		double l0;
		double l1;
		double r0;
		double r1;
		double e;
		double el;
		double er;
		double total;
		double ig = 0;
		double pe;
		double max = 0;
		int threshold = 0;
		int attribute = 0;
		double el0;
		double el1;
		double er0;
		double er1;
		double zero;
		double one;
		//System.out.println("Size: " + data.size());
			for (int i = 0; i < data.get(0).size() - 1; i++) {
				for (int j = 1; j < 10; j++) {
					l0 = 0;
					l1 = 0;
					r0 = 0;
					r1 = 0;
					el0 = 0;
					el1 = 0;
					er0 = 0;
					er1 = 0;
					e = 0;
					el = 0;
					er = 0;
					total = 0;
					ig = 0;
					pe = 0;
					zero = 0;
					one = 0;
					for (int k = 0; k < data.size(); k++) {
						//System.out.println("Label: " + label);
						//System.out.print(j + " == " + data.get(k).get(i) + ", ");
						if (data.get(k).get(i) > j) {
							//System.out.println("hits");
							if (data.get(k).get(data.get(k).size() - 1) == 1) {
								r1++;
							}
							else r0++;
						}
						else {
							if (data.get(k).get(data.get(k).size() - 1) == 1) {
								l1++;
							}
							else l0++;
						}
					}
						total = r0 + r1 + l0 + l1;
						
						if (r0 == 0) {
							er0 = 0;
						}
						else {
							er0 = (r0/(r0 + r1)*Math.log(r0/(r0 + r1))/Math.log(2));
						}
						if (r1 == 0) {
							er1 = 0;
						}
						else {
							er1 = (r1/(r0 + r1)*Math.log(r1/(r0 + r1))/Math.log(2));
						}
						if (l0 == 0) {
							el0 = 0;
						}
						else {
							el0 = (l0/(l0 + l1)*Math.log(l0/(l0 + l1))/Math.log(2));
						}
						if (l1 == 0) {
							el1 = 0;
						}
						else {
							el1 = (l1/(l0 + l1)*Math.log(l1/(l0 + l1))/Math.log(2));
						}
						//System.out.println("I: " + i + " J: " + j + " K: " + k);
						//System.out.println("J: " + j + " Value: " + data.get(k).get(i) + " R0: " + r0 + " R1: " + r1 + " L0: " + l0 + " L1: " + l1 + " Total: " + total + " ClassLabel: " + (data.get(k).get(data.get(k).size() - 1)));
						er = ((r1 + r0)/total) * (er0 + er1);
						el = ((l1 + l0)/total) * (el0 + el1);
						e = -(el + er);
						if (r0 + l0 == 0) {
							zero = 0;
						}
						else {
							zero = ((r0 + l0)/total)*Math.log((r0 + l0)/total)/Math.log(2);
						}
						if (r1 + l1 == 0) {
							one = 0;
						}
						else {
							one = ((r1 + l1)/total)*Math.log((r1 + l1)/total)/Math.log(2);
						}
						pe = -(zero + one);
						ig = pe - e;
						
						
						if (ig > max) {
							max = ig;
							threshold = j;
							attribute = i;
						}
					
					//System.out.println("");
				}
			}
			
			if (max <= 0) {
				if (classLabel(data)/data.size() >= 0.5) {
					DecTreeNode node = new DecTreeNode(1, 0, 0);
					return node;
				}
				else {
					DecTreeNode node = new DecTreeNode(0, 0, 0);
					return node;
				}
			}
			//System.out.println("IG: " + max);
			List<List<Integer>> right = new ArrayList<List<Integer>>();
			List<List<Integer>> left = new ArrayList<List<Integer>>();
			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).get(attribute) > threshold) {
					right.add(data.get(i));
				}
				else left.add(data.get(i));
			}
		
			
			DecTreeNode node = new DecTreeNode(1, attribute, threshold);
			node.right = buildTree(depth + 1, right);
			node.left = buildTree(depth + 1, left);
			return node;
	}
	
	public int classify(List<Integer> instance) {
		// TODO: add code here
		// Note that the last element of the array is the label.
		DecTreeNode node = root;
		
		while(!node.isLeaf()) {
			if (instance.get(node.attribute) <= node.threshold) {
				node = node.left;
			}
			else {
				node = node.right;
			}
		}
		return node.classLabel;
	}
	
	// Print the decision tree in the specified format
	public void printTree() {
		printTreeNode("", this.root);
	}

	public void printTreeNode(String prefixStr, DecTreeNode node) {
		String printStr = prefixStr + "X_" + node.attribute;
		System.out.print(printStr + " <= " + String.format("%d", node.threshold));
		if(node.left.isLeaf()) {
			System.out.println(" : " + String.valueOf(node.left.classLabel));
		}
		else {
			System.out.println();
			printTreeNode(prefixStr + "|\t", node.left);
		}
		System.out.print(printStr + " > " + String.format("%d", node.threshold));
		if(node.right.isLeaf()) {
			System.out.println(" : " + String.valueOf(node.right.classLabel));
		}
		else {
			System.out.println();
			printTreeNode(prefixStr + "|\t", node.right);
		}
	}
	
	public double printTest(List<List<Integer>> testDataSet) {
		int numEqual = 0;
		int numTotal = 0;
		for (int i = 0; i < testDataSet.size(); i ++)
		{
			int prediction = classify(testDataSet.get(i));
			int groundTruth = testDataSet.get(i).get(testDataSet.get(i).size() - 1);
			System.out.println(prediction);
			if (groundTruth == prediction) {
				numEqual++;
			}
			numTotal++;
		}
		double accuracy = numEqual*100.0 / (double)numTotal;
		System.out.println(String.format("%.2f", accuracy) + "%");
		return accuracy;
	}
}
