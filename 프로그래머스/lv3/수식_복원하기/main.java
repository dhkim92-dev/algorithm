import java.util.*;
import java.util.stream.Collectors;

class Solution {
    
    // n진법 수를 10진수로 변환한다.
    private int convertToTenary(int n, int value) {
        int first = value / (100);
        value %= 100;
        int second = value / 10;
        int third = value % 10;
        
        return first * (n*n) + second * n + third;
    }
    
    // 10진법 수를 n진수로 변환한다.
    private int convertToNary(int n, int value) {
        int first = value  / (n*n);
        value %= (n*n);
        int second = value / n;
        int third = value % n;
        
        return first * 100 + second * 10 + third;
    }
    
    private int calc(int n, String exp) {
        //System.out.println(n+"진수 수식 : " + exp);
        String[] token = exp.split(" ");
        int lop = Integer.parseInt(token[0]);
        int rop = Integer.parseInt(token[2]);
        int op = token[1].equals("+") ? 1 : -1;
        return calc(n, lop, rop, op);
    }
    
    // n진수로 수식 결과를 계산한다.
    private int calc(int n, int lop, int rop, int op) {
        int tLop = convertToTenary(n, lop);
        int tRop = convertToTenary(n, rop);
        int tRes = tLop + op * tRop;
        //System.out.println("십진수 결과 : " + tRes);
        int nRes = convertToNary(n, tRes);
        //System.out.println(n+"진수 결과 : " + nRes);
        return nRes;
    }
    
    // 수식에 제공된 숫자들 기반으로 n진수 후보군을 구한다.
    private Set<Integer> getBaseCandidates(String[] expressions) {
        Set<Integer> candidates = new HashSet<>(Arrays.asList(2,3,4,5,6,7,8,9));
        
        int maxValue = 0;
        
        for(String exp : expressions) {
            String[] token = exp.split(" ");
            int lOp = Integer.parseInt(token[0]);
            int rOp = Integer.parseInt(token[2]);
            
            int lOpFirst = lOp/10;
            int lOpSecond = lOp%10;
            int rOpFirst = rOp/10;
            int rOpSecond = rOp%10;
            //System.out.println(lOpFirst + " " + lOpSecond + " " + rOpFirst + " " + rOpSecond);
            
            maxValue = Math.max(maxValue, Math.max(lOpFirst, lOpSecond));
            maxValue = Math.max(maxValue, Math.max(rOpFirst, rOpSecond));
            if(!token[4].equals("X")) {
                int res = Integer.parseInt(token[4]);
                int resFirst = res/100;
                res%=100;
                int resSecond = res/10;
                int resThird = res%10;
               // System.out.println("res " + resFirst + " " + resSecond + " " + resThird);
                maxValue = Math.max(maxValue, Math.max(resThird, Math.max(resFirst, resSecond)));
                //System.out.println("tmp maxValue : " + maxValue);
            }
        }
        
        for(int i = 2 ; i <= maxValue ; i++) {
            candidates.remove(i);
        } 
        
        return candidates;
    }
    
    private void reduceCandidatesByResult(Set<Integer> candidates, List<String> expressions) {
        for(String exp : expressions) {
            int minValue = 10;
            List<Integer> removeTargets = new ArrayList<>();
            for(int candidate : candidates) {
                int res = calc(candidate, exp);
                //System.out.println("expression : " + exp + " result : " + res);
                if(!String.valueOf(res).equals(exp.split(" ")[4])) {
                    removeTargets.add(candidate);
                }
            }
            candidates.removeAll(removeTargets);
        }
    }
    
    private String replaceExpression(Set<Integer> candidates, String exp) {
        Set<Integer> results = new HashSet<>();
        
        for(int candidate : candidates) {
            results.add(calc(candidate, exp));
        }
        
        if(results.size() == 1) {
            int res = results.stream()
                .collect(Collectors.toList())
                .get(0);
            return exp.replace("X", String.valueOf(res));
        }
        
        return exp.replace("X", "?");
    } 
    
    public String[] solution(String[] expressions) {
        String[] answer = {};
        List<String> knownExpressions = Arrays.stream(expressions)
            .filter(it -> !it.contains("X"))
            .collect(Collectors.toList());
        List<String> unknownExpressions = Arrays.stream(expressions)
            .filter(it -> it.contains("X"))
            .collect(Collectors.toList());
        answer = new String[unknownExpressions.size()];
        
        Set<Integer> candidates = getBaseCandidates(expressions);
        reduceCandidatesByResult(candidates, knownExpressions);
        
        for(int i = 0 ; i < unknownExpressions.size() ; i++) {
            answer[i] = replaceExpression(candidates, unknownExpressions.get(i));
        }
        
        return answer;
    }
}
