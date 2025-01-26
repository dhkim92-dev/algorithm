
class Solution {

    private int N;
    private int[] inputs;
    private char[] oprts = {'+', '-', ' '};
    private int[] priority = {1, 1, 2};

    static class Element {
        char ch;
        int priority;

        Element(char ch) {
//            System.out.println("new Element with char + " + String.valueOf(ch));
            this.ch = ch;
            if(ch >= '1' && ch <= '9') {
                this.priority = 0;
            } else if(ch == '+') {
                this.priority = 1;
            } else if(ch == '-') {
                this.priority = 1;
            } else {
                this.priority = 2;
            }
        }
    }

    public Solution(BufferedReader reader) throws IOException {
        N = Integer.parseInt(reader.readLine());
        inputs = new int[N];

        for(int i = 0 ; i < N ; i++) {
            inputs[i] = Integer.parseInt(reader.readLine());
        }
    }

    private int calculate(int[] numbers, char[] operators) {
        Stack<Element> st1 = new Stack<>();
        Stack<Element> st2 = new Stack<>();

        // 후위 표기식으로 변환
        for(int i = 0 ; i < operators.length ; i++) {
            if(i == 0) {
                st2.push(new Element((char)(numbers[i] + '0')));
                st2.push(new Element((char)(numbers[i+1] + '0')));
                st1.push(new Element(operators[i]));
            } else {
                Element newOprt = new Element(operators[i]);
                Element newOperand = new Element((char)(numbers[i+1] + '0'));

                while(!st1.isEmpty() && st1.peek().priority >= newOprt.priority) {
                    st2.push(st1.pop());
                }
                st1.push(newOprt);
                st2.push(newOperand);
            }
        }

        while(!st1.isEmpty()) {
            st2.push(st1.pop());
        }

        // 후위표기식 출력
//        System.out.println("Postfix expression");
        while(!st2.isEmpty()) {
            st1.push(st2.pop());
        }
//        while(!st1.isEmpty()) {
//            Element e = st1.pop();
//            if(e.ch == ' ') {
//                System.out.print(" ");
//            } else {
//                System.out.print(e.ch + " ");
//            }
//        }
//        System.out.println();

        int calc = 0;

        while(!st1.isEmpty()) {
            Element e = st1.pop();
            if(e.priority == 0) {
                st2.push(e);
            } else {
                Element op2 = st2.pop();
                Element op1 = st2.pop();
                int num1 = op1.ch - '0';
                int num2 = op2.ch - '0';
                int res = 0;
                if(e.ch == '+') {
                    res = num1 + num2;
                } else if(e.ch == '-') {
                    res = num1 - num2;
                } else {
                    res = num1 * 10 + num2;
                }
                st2.push(new Element((char)(res + '0')));
            }
        }

        calc = st2.pop().ch - '0';
        return calc;
    }

    private String makeExpression(int[] numbers, char[] operators) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < numbers.length ; i++) {
            sb.append(numbers[i]);
            if(i < operators.length) {
                sb.append(operators[i]);
            }
        }
        return sb.toString();
    }

    private void dfs(int depth, char[] operators, int[] numbers, List<String> expressions) {
        if(depth == operators.length) {
            int res = calculate(numbers, operators);
            if(res == 0) {
                expressions.add(makeExpression(numbers,operators));
            }
            return;
        }

        for(int i = 0 ; i < 3 ; i++) {
            operators[depth] = oprts[i];
            dfs(depth + 1, operators, numbers, expressions);
        }
    }

    public void run() throws IOException {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < inputs.length ; i++) {
            List<String> expressions = new ArrayList<>();
            char[] operators = new char[inputs[i] - 1];
            int[] numbers = IntStream.range(1, inputs[i] + 1).toArray();
            dfs(0, operators, numbers, expressions);
            Collections.sort(expressions);
            for(String expression : expressions) {
                sb.append(expression);
                sb.append("\n");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}

