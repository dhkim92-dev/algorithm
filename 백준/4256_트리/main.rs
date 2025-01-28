ulse std::io;

trait Solution {
    fn run(& self);
}

struct Problem4256 {
    T: i32,
    node_counts: Vec<i32>,
    inorders: Vec<Vec<i32>>,
    preorders: Vec<Vec<i32>>
}

struct Node {
    value: i32,
    left:  Option<Box<Node>>,
    right: Option<Box<Node>>
}

impl Solution for Problem4256 {

    fn run(& self) {

        for i in 0..self.T {
            let preorder = &self.preorders[i as usize];
            let inorder = &self.inorders[i as usize];
            let root = self.recoverTree(preorder, inorder);
            self.postOrder(root);
            println!("");
        }
    }
}

impl Problem4256 {

    fn recoverTree<'a>(& self, preorder: &'a Vec<i32>, inorder: &'a Vec<i32>) -> Option<Box<Node>> {
        if preorder.is_empty() {
            return None
        }

        let root = preorder[0];
        let rootIndex = inorder.iter().position(|&x| x == root).unwrap();
        let leftInorder = &inorder[0..rootIndex as usize].to_vec();
        let rightInorder = &inorder[(rootIndex+1) as usize..inorder.len()].to_vec();
        let leftPreorder = &preorder[1..rootIndex+1 as usize].to_vec();
        let rightPreorder = &preorder[(rootIndex+1) as usize .. preorder.len()].to_vec();

        Some(Box::new(Node {
            value: root,
            left: self.recoverTree(leftPreorder, leftInorder),
            right: self.recoverTree(rightPreorder, rightInorder)
        }))
    }

    fn postOrder(& self, root: Option<Box<Node>>) {
        if let Some(node) = root {
            self.postOrder(node.left);
            self.postOrder(node.right);
            print!("{} ", node.value);
        }
    }
}

fn main() {
    let mut buf: String = String::new();
    let T: i32 = read_line().parse().unwrap();
    let mut nodes: Vec<i32> = Vec::new(); // vec![0; T as usize];
    let mut preorders: Vec<Vec<i32>> = Vec::new();
    let mut inorders: Vec<Vec<i32>> = Vec::new();

    for i in 0..T {
        let N: i32 = read_line().parse().unwrap();
        let preorder = read_line().split_whitespace().map(|x| x.parse().unwrap()).collect();
        let inorder = read_line().split_whitespace().map(|x| x.parse().unwrap()).collect();
        nodes.push(N);
        preorders.push(preorder);
        inorders.push(inorder);
    }

    let problem = Problem4256{ T: T, node_counts: nodes, inorders: inorders, preorders: preorders };
    problem.run();
}

fn read_line() -> String {
    let mut buf = String::new();
    std::io::stdin().read_line(&mut buf).unwrap();
    buf.trim().to_string()
}
