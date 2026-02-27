## yaml파일 작성

- yaml파일 수동작성시 apiVersion 확인 하는 방법  (kubectl explain ?(kind 부분))
```bash
kubectl explain pod
```

- pod용 yaml => 여러종류의 yaml파일이 생긴다(레플리카, 노드, 네임스페이스 등) + ---(구분자)를 통해 하나의 yaml로 여러개의 pod를 만들 수 있다.
```yaml
apiVersion: v1
kind: Pod

metadata: 
  name: [Pod 이름]

spec:
  containers:
  - image: [docker 이미지 이름]
    name: [컨테이너 이름]

---
apiVersion: v1
kind: Pod

metadata: 
  name: [Pod 이름]

spec:
  containers:
  - image: [docker 이미지 이름]
    name: [컨테이너 이름]
```

## labels: 가 있는데 레플리카가 name말고 label로 실행하기 때문에 레플리카관련 동작이 제대로 될 수 없으므로 라벨 무조건 넣는게 좋다. resources: {} 는 cpu와 메모리 사용하게 주는 기준인데 없으면 기본으로 처리 된다.

- Deployment용 yaml(kubectl explain deployment로 확인가능) + 셀렉터와 템플릿 내의 app: [Pod 라벨명]을 일치 시켜야 한다.
```bash
apiVersion: apps/v1
kind: Deployment

metadata:
    name: [Deployment 이름]

spec:
    replicas: [Pod 수]
    selector:
        matchLabels:
            app: [Pod 라벨명]
    template:
        metadata: 
            labels:
                app: [Pod 라벨명]

        spec:
            containers:
                - image: [Docker 이미지]
                    name: [Container 이름]
                    ports:
                    - containerPort: [Service 포트]
```

- yaml 만들기 ( 라벨명을 생성시 만들수 없기때문에 나중에 수동으로 수정해야한다.)
```bash
kubectl create deployment app-dp --replicas=2 --image=nginx:1.28 --dry-run -o yaml > app-dp.yaml
```

- 레플리카 개수 등을 수정하기
```bash
kubectl edit deployment/app-dp
kubectl apply -f app-dp.yaml
```

- Service 만들기( 내부 하드 바깥쪽포트 연결해야해서 3개의 포트)
```yaml
apiVersion: v1
kind: Service

metadata:
    name: app-sv

spec:
    type: NodePort
    selector:
        app: app-label
    ports:
        - protocol : TCP
          port: 80
          targetPort: 80
          nodePort: 30000
```
- 실행 코드
```bash
kubectl create -f app-sv.yaml
```