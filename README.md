# 데이터 흐름

1. [SelectSchedulerInputAdapter.java](src/main/java/com/nanoit/agent/hexagonal/data/SelectSchedulerInputAdapter.java)
2. [MessageUseCase.java](src/main/java/com/nanoit/agent/application/MessageUseCase.java)
3. [TransportOutputPort.java](src/main/java/com/nanoit/agent/application/TransportOutputPort.java)

- [ToKtApiTransportOutputPort.java](src/main/java/com/nanoit/agent/hexagonal/transport/ToKtApiTransportOutputPort.java)
- [ToNanoitApiTransportOutputPort.java](src/main/java/com/nanoit/agent/hexagonal/transport/ToNanoitApiTransportOutputPort.java)

5. [H2PersistenceOutputPort.java](src/main/java/com/nanoit/agent/hexagonal/data/H2PersistenceOutputPort.java)

# adapter

- 데이터 시작점

# port

- 데이터 흐름

# spring hexagonal agent

- spring hexagonal architecture 기반 agent 교육 프로젝트

# goal

- [ ] 전송 테이블 설계
- [ ] 전송 테이블에서 데이터를 가지고 올 수 있다
- [ ] 전송 테이블에서 데이터를 업데이트 할 수 있다
- [ ] hexagonal architecture core 영역으로 데이터를 전달 할 수 있다
- [ ] hexagonal architecture core 영역에서 데이터를 검증 할 수 있다
- [ ] hexagonal architecture core 영역에서 transport adapter 로 데이터를 전달 할 수 있다
- [ ] transport adapter 영역에서 netty 기반으로 서버와 통신할 수 있다

# branch

## main

- 관리자 브랜치로 관리자만 개발 및 접근한다.

## main-kny

- 교육생 이니셜 전용 브랜치

## main-kyg

- 교육생 이니셜 전용 브랜치

## main-pjh

- 교육생 이니셜 전용 브랜치

## main bmj

- 교육생 이니셜 전용 브랜치