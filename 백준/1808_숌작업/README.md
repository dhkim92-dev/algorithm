
## 해설
Greedy와 Dynamic Programming 두가지 방식으로 풀이가 가능하다.
어떤 방식을 선택하더라도 반드시 파악해야하는 점은 어떤 루트가 아닌 노드 W가 있고, W의 임의의 조상 V가 있다고 할 때, V를 루트로 하는 서브 트리를 연산을 통해 레벨을 감소시키면, W 역시 그 레벨이 같이 감소한다는 것이다.

주어진 예제 입력 1번에 대해 생각해보자. 그래프는 아래 그림과 같이 나타날 것이다

<p align="center"> <img src="https://www.dohoon-kim.kr/media/images/2/250c8d40-46c9-4fff-b417-5e0557744256.png"/> </p>

이 그림에서 2번 노드에 대해 연산을 진행하고 선조 노드 0번 노드를 선택하면
아래 그림과 같이 변화한다.  

<p align="center"> <img src="https://www.dohoon-kim.kr/media/images/e/e5b1127b-c26f-4a76-9c97-03b195847558.png"/> </p>

한번의 연산을 통해 전체 그래프의 레벨을 1 감소시킨 것을 확인할 수 있다.
이 점을 유념하면서 Dynamic Programming 을 적용해보자

## 동적 계획법

```java
int [][]dp = new int[노드의 수][최대 가능 레벨];
for(int i = 0 ; i < dp.length ; i++) {
    Arrays.fill(dp[i], -1);
}
```

여기서 dp 배열은 다음과 같은 값을 나타낸다.
```java
// dp[i][j] => 현재 레벨이 j이고, 루트가 i인 서브트리를 레벨 H로 만드는데 들어가는 최소 비용.
```

그 다음으로 숌 연산 비용을 구할 수 있는 재귀함수 cost(index, height) 를 정의한다.

```java
private int cost(int index, int height) {
    // 이 함수는 index 번째 노드를 루트로 하고, index의 현재 레벨이 height 인 
    // 서브트리의 높이를 H로 만드는데 필요한 비용을 반환한다.
    // 비용 값은 dp[index][height]에 메모이제이션 된다.
    ...
    if(dp[index][height] != -1) {
        return dp[index][height];
    }

    return dp[index][height];
}
```

자 이제 각 서브트리의 탐색 조건을 어떻게 분기 시켜야하는지 살펴보자
```txt
a. 현재 탐색 리벨이 0이라면 전체 그래프의 최상단이며, 이 트리는 선택 자체가 불가능하다.
그렇기 때문에 곧바로 자식트리로 탐색을 진행한다.
b. 현재 레벨이 H와 동일하다면 현재 노드에 대해 연산을 진행함으로써 자식 노드들이 구성하는 각 서브 트리의 레벨을 한번에 낮출 수 있다.
c. 그 외의 경우에는 현재 노드에 연산을 진행하는 경우와, 자식 노드들에 대해서만 연산을 진행하는 경우로 나눌 수 있다.
```

위 조건을 그림으로 살펴보자


<p align="center"> <img src="https://www.dohoon-kim.kr/media/images/4/42780613-9e09-4610-946a-691ca265c493.png"/> </p>
<center>조건 a</center>

현재 인덱스의 노드가 루트라면 연산 자체를 진행할 수 없기 때문에
그림 상 분홍색으로 표기된 노드들로 구성된 서브 트리에 대해서만 연산이 가능하다. 이걸 식으로 풀어쓰면 아래와 같다.

```java
ret += cost(child, height+1); 
```


<p align="center"> <img src="https://www.dohoon-kim.kr/media/images/c/c075168e-8a7d-44ed-a4ec-cb2af2e19308.png"/> </p>
<center>조건 b</center>

현재 인덱스의 노드의 레벨이 목표와 동일하다면, 해당 노드에 연산을 진행하여 오른쪽 트리와 같이 자식 노드들이 구성하는 서브트리의 레벨을 낮출 수 있다.

<p align="center"> <img src="https://www.dohoon-kim.kr/media/images/6/6fa76e5f-743d-439d-aee6-8f8bec824a93.png"/> </p>
<center>조건 c</center>

이 경우에는 자기 자신에 대해 연산을 진행하여 서브트리의 높이를 낮추는 비용(우측 상단 트리)과
자식들이 자신과의 연결을 끊고 현재 노드의 부모 노드와 연결하는 비용(우측 하단 트리)의 합 중 작은 것을 취해야 한다.

위 조건에 따라 자식 노드들에 대한 재귀 탐색 코드를 작성하면 아래와 같다.

``` java
int cost(int width, int height) {
    ...
    for(int child : childrens) {
        if(height == 0) {
            ret += cost(child, height+1);
        } else if(height == H) {
            // 현재 노드에 대해 연산한 값을 1 더해주고, 자식 노드로 탐색을 한다.
            // 이 떄 자식 노드는 레벨이 1 낮아지므로 height 를 그대로 넘겨준다.
            ret += cost(child, height) + 1;
        } else {
            // 현재 노드에 대해 연산한 경우의 비용과
            // 현재 노드에 대해 연산을 하지 않은 경우 자식 노드의 서브트리에서 발생하는 비용을 합한 값의 최소값을 취한다.
            ret += Math.min(cost(child, height) + 1, cost(child, height+1));
        }
    }
    ...
}

```
