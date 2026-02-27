- dockerfile 내용

```bash
# --- 1단계: Build (의존성 설치) ---
FROM python:3.13.11 AS build
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

WORKDIR /workspace

# 의존성 파일 복사 및 설치
COPY pyproject.toml uv.lock ./
# 가상환경(.venv) 생성
RUN uv sync --frozen --no-dev

# --- 2단계: Run (실행 전용) ---
FROM python:3.13.11

WORKDIR /workspace

# 빌드 단계에서 생성된 환경과 소스 복사
COPY --from=build /workspace /workspace
COPY . .

# 가상환경을 PATH에 추가
ENV PATH="/workspace/.venv/bin:$PATH"
# 파이썬이 현재 폴더의 모듈을 인식하도록 설정
ENV PYTHONPATH="/workspace"

EXPOSE 8000

# fastapi standard 실행 명령어
CMD ["fastapi", "run", "main.py", "--host", "0.0.0.0", "--port", "8000"]
```

- dockerfile 강사님꺼

```bash
FROM python:3.13.11

RUN apt-get update
RUN apt-get upgrade -y
RUN curl -LsSf https://astral.sh/uv/install.sh | sh
RUN pip install uv

WORKDIR /workspace

EXPOSE 8000
```

```bash
docker build -t uv:1 .
```

## APP1 Container 생성

```bash
docker run -d -it -p 8001:8000 -v ./app1:/workspace --name app1 uv:1
```

-IP :172.17.0.3 ***app1 IP***

- network연결

```bash

```



# kafka만들기
```bash
docker run -d --name kafka -e KAFKA_NODE_ID=1 -e KAFKA_PROCESS_ROLES=broker,controller -e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@kafka:9093 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 -e KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0 -e KAFKA_NUM_PARTITIONS=3 apache/kafka:4.0.1
```

```bash
docker network inspect bridge
```

-IP :172.17.0.2 ***kafka IP***

