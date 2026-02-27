# 쿠버네틱스에서 namespace는 파일의 기능이나 pods들을 구분하기 위해 namespace안에 기능적으로 분류시키는 기능 => node안에 있고 node안에 여러개의 namespace만들수있고 논리적으로만 나뉘어져있다. 물리적 X

# 파드에서 컨테이너 하나가 기본이지만 기능에 따라 나눌때도 있다.

## 디플로이먼트 버젼이 올라와도 기존 서비스는 진행하면서도 업데이트 할 수 있게 해준다. 만약에 레플리카셋에서 구 파드를 내리는 중에 문제가 생기면 롤링백으로 사용해서 구 파드를 다시 살리는 기능을 한다.

## 디플로이먼트 : 레플리카를 여러 개 만들어 롤링 업과 롤링다운 지시. 레플리카: 파드수를 보장해주는 기능 하나의 디플로이먼트에 여러개의 레플리카 생성가능. 와 파드: 안의 내용물이 컨테이너로 구성 보통 1 와의 관계 생각해보자

# 쿠버네틱스 클러스터 내에서 서비스를 한다는 것은 외부와 내부를 portforwarding해서 자동 접속 연결해서 pod를 연결시켜준다 생각하면된다. 

## `Namespace` 구성하기 == 작업공간 분리했다.

- 목록 보기
```bash
kubectl get namespaces
```

- 현재 사용 중인 context 확인
```bash
kubectl config get-contexts
```

- ns 생성
```bash
kubectl create namespace n1
```
## 생성된 ns를 올릴라면 context를 사용해라?

- yaml 생성
```bash
kubectl create namespace n2 --dry-run -o yaml > n2-ns.yaml
```

--dry-run => 테스트 하는데 잘 만들어지는지 확인해봐

- yaml 파일 실행

```bash
kubectl create -f n2-ns.yaml
```

- pod 생성 (-n을 통해 namespace 위치 지정)
```bash
kubectl run web --image=nginx:1.28 --port 80 -n n1 --dry-run -o yaml
```

- 모든 ns에서 파드 확인하기 & n1 네임스페이스에서 확인하기
```bash
kubectl get pods --all-namespaces
kubectl get pods -n n1
```

- 매번 -n n1하기 귀찮으니 n1으로 이동하자 => config context이용한다 => Context생성 + kubectl config --help 치면 목록 나옴

- kubectl config view로 클러스터 유저 네임스페이스 이름 ㄱ => context 생성 및 등록
```bash
kubectl config set-context n1-context --cluster=docker-desktop --user=docker-desktop  --namespace=n1
```

- Context 교체 (n1-context 생성된걸로)
```bash
kubectl config use-context n1-context
```

- 사용 후 Context 삭제 & current-context 위치 제자리로 변경(docker-desktop는 context 클러스터 유저 이름)
```bash
kubectl config delete-context n1-context
kubectl config use-context docker-desktop
```

- 클러스터 실행 상태 확인 (죽어 있으면 모든게 실행 안되니 문제시 확인)
```bash
kubectl cluster-info
```

- watch 이용 실시간 pods사용확인
```bash
kubectl get pods --watch
```

