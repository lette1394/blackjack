# blackjack
카드의 숫자를 더해서 `21`을 만들면 되는 간단한 게임


## 기본적인 rule
- 사용하는 카드
  - 사적인 게임에서는 조커를 제외한 52장을, 
  - 카지노에서는 보통 여러 벌을 사용
- 참가하는 인원수: 2~8명
- 점수 계산
  -  숫자카드(2~10) 표시된 그대로 점수
  - K, Q, J는 10점 
  - 에이스(A)는 1점 또는 11점 둘 중의 하나로 계산하는데, 플레이어가 유리한 쪽으로 계산할 수 있다.
 

## 배팅하기
1. 배팅 금액을 정할 수 있다.
2. 이기는 경우 -> 2배
3. 지는 경우 -> 잃는다 
  - 예를 들어, 1000원을 걸어서 이기면 2000원을 받고 (수익: 1000원), 지면 1000원을 잃는다 (수익: -1000원)
4. 배팅률이나 정책은 달라질 수 있다.


## 게임 진행하기
1. 카드 두 장을 기본적으로 받는다.
2. 카드 두 장의 합계가 21이 되지 않는 경우 얼마든지 카드를 계속 뽑을 수 있다. 뽑지 않을 수도 있다. 
  - 숫자의 합이 21을 **초과**하는 경우 Bust 라고 하며, 딜러의 결과와 관계없이 무조건 패배한다.
3. 딜러는 플레이어의 카드 추가가 모두 끝난 뒤에, 정해진 규칙에 따라 본인이 카드를 더 받을 것인지를 결정한다. 
  - 딜러의 합계가 16점 이하이면 반드시 1장을 더 받아야 하고, 
  - 17점 이상이면 카드를 더 받지 않고 멈춘다.
4. 딜러의 점수와 비교해서 플레이어의 승패 여부를 가린다. 21에 더 가까운 사람이 승리한다.  


## 상세 rule
## BLACKJACK: 처음 받은 두 장을 합쳐 21이 나오는 경우 
- 배팅한 금액의 1.5배를 얻는다. 
- e.g. 1000원을 배팅했으면 승리시 2000원을 얻어야 하나, 블랙잭으로 승리하는 경우 2500원을 얻는다.  

## Push: 딜러와 플레이어가 동시에 blackjack
- 자신이 배팅한 금액을 그대로 돌려받는다.

## Bust: 딜러와 플레이어가 동시에 Bust
- 플레이어만 패배한다. 
- 즉, 둘 다 Bust 이면 딜러가 이긴다.

## Hit

## Stay

## Split

## Double Down

## Insurance





============
## TODO
- [x] 게임 참여 
- [x] 게임 승리
- [x] 게임 패배
- [x] 카드 분배 후 hit/stay 결정 
- [x] 여러 게임 
- [x] 딜러 한 명, 플레이어 한 명 
- [ ] 비기는 상황 
- [ ] 베팅
- [ ] 파산 
- [ ] 연속 게임의 경우 여러 덱 사용 (덱 고갈 상황)
- [ ] 딜러 한 명, 플레이어 여러 명
- [ ] 게임 통계
- [ ] 21과 blackjack 구분 
- [ ] 기타 추가 규칙 구현


=========
### Game Status 
[![](https://mermaid.ink/img/eyJjb2RlIjoic3RhdGVEaWFncmFtXG4gICAgWypdIC0tPiB3YWl0aW5nXG4gICAgZmluaXNoaW5nIC0tPiBbKl1cbiAgICBcblx0d2FpdGluZyAtLT4gcnVubmluZyA6IGpvaW4gXG4gICAgcnVubmluZyAtLT4gcnVubmluZzogcmVqb2luXG4gICAgcnVubmluZyAtLT4gZmluaXNoaW5nIDogbGVhdmVcblx0d2FpdGluZyAtLT4gd2FpdGluZyA6IGhpc3RvcnlcblxuXHRzdGF0ZSBydW5uaW5nIHtcbiAgICAgICAgWypdIC0tPiBiZXR0aW5nXG4gICAgICAgIGJldHRpbmcgLS0-IGRyYXdpbmcgOiBiZXRcbiAgICAgICAgZHJhd2luZyAtLT4gZHJhd2luZyA6IGhpdCBcbiAgICAgICAgZHJhd2luZyAtLT4gc2NvcmluZyA6IHN0YXlcbiAgICAgICAgc2NvcmluZyAtLT4gWypdXG4gICAgfSIsIm1lcm1haWQiOnsidGhlbWUiOiJuZXV0cmFsIn19)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoic3RhdGVEaWFncmFtXG4gICAgWypdIC0tPiB3YWl0aW5nXG4gICAgZmluaXNoaW5nIC0tPiBbKl1cbiAgICBcblx0d2FpdGluZyAtLT4gcnVubmluZyA6IGpvaW4gXG4gICAgcnVubmluZyAtLT4gcnVubmluZzogcmVqb2luXG4gICAgcnVubmluZyAtLT4gZmluaXNoaW5nIDogbGVhdmVcblx0d2FpdGluZyAtLT4gd2FpdGluZyA6IGhpc3RvcnlcblxuXHRzdGF0ZSBydW5uaW5nIHtcbiAgICAgICAgWypdIC0tPiBiZXR0aW5nXG4gICAgICAgIGJldHRpbmcgLS0-IGRyYXdpbmcgOiBiZXRcbiAgICAgICAgZHJhd2luZyAtLT4gZHJhd2luZyA6IGhpdCBcbiAgICAgICAgZHJhd2luZyAtLT4gc2NvcmluZyA6IHN0YXlcbiAgICAgICAgc2NvcmluZyAtLT4gWypdXG4gICAgfSIsIm1lcm1haWQiOnsidGhlbWUiOiJuZXV0cmFsIn19)

```
stateDiagram
    [*] --> waiting
    finishing --> [*]
    
    waiting --> running : join 
    running --> running: rejoin
    running --> finishing : leave
    waiting --> waiting : history

    state running {
        [*] --> betting
        betting --> drawing : bet
        drawing --> drawing : hit 
        drawing --> scoring : stay
        scoring --> [*]
    }
```