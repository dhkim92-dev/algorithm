# 2023 Kakao blind recruitment 미로 탈출 명령어
## 문제 링크
https://school.programmers.co.kr/learn/courses/30/lessons/150365
## 힌트
* 이동해야하는 거리 K 와 도착점과 시작점의 r,c 델타 값의 절대값 합의 차가 홀수 일 경우  
절대로 목표 지점에 도달할 수 없다.  
* K - abs(delta x) - abs(delta c) 의 차 만큼 남은 문자열을 배열하여 사전 순으로 가장 빠른  
결과를 만들어야한다.

## 해결방법
1. 힌트에 적힌 방법을 통해 delta r, delta c를 구하고 풀이가 가능한지 먼저 판단.
2. 가능하다면 D, L, R, U 순으로 현재 상태에서 입력이 가능한지 확인하고, 가능하다면 무조건 집어넣는다.

