- [Project Root로 돌아가기](../../README.md)

- [AWS EC2 Notion에서 보기](https://danghyeona.notion.site/AWS-EC2-3febc31140c2404d90b640bb7e690521) : Ubuntu, WSL2 설치 및 MobaXterm Download, pem.key 세팅 등


## 목차
- [목차](#목차)
- [🛠 MobaXterm EC2 연결](#-mobaxterm-ec2-연결)
- [🗄 EC2에서 MySQL 세팅하기](#-ec2에서-mysql-세팅하기)
- [🖥 Local MySQL WorkBench에서 연동하기](#-local-mysql-workbench에서-연동하기)

## 🛠 MobaXterm EC2 연결

1. MobaXterm에서 왼쪽 상단의 `Session` 으로 접속합니다.

    ![image](https://user-images.githubusercontent.com/45550607/128296824-a0b0f7ac-a929-45fa-88ee-4fbe0d884c17.png)


2. 새로운 팝업이 뜨면 아래와 같이 보이고 왼쪽 상단의 `SSH` 를 눌립니다.

    ![image](https://user-images.githubusercontent.com/45550607/128296867-62aeb3a6-787f-4c41-baf7-6e48d41ebec8.png)

    - OPEN API의 경우 : 예시 `ubuntu@i5a507` 처럼 ubuntu@를 붙여서 진행해야 한다.
    - pem 파일을 연결하기 전에 local에서 해당 파일의 보안을 설정해주어야 한다.
  
3. 연결 성공 완료

    ![image](https://user-images.githubusercontent.com/45550607/128296912-01ad32ca-5003-4cd5-88eb-614c82361d0a.png)

## 🗄 EC2에서 MySQL 세팅하기

> 해당 명령어는 MobaXterm에서 입력하는 Liunx command입니다.

1. 세팅을 위해 최신 상태로 업데이트입니다.

    ```bash
    sudo apt-get update
    ```

2. MySQL을 설치합니다.

    ```bash
    sudo apt-get install mysql-server
    ```

3. 설치를 확인하기 위한 명령어를 확인합니다.

    ```bash
    dpkg -l | grep mysql-server
    ```

4. 추가적인 세팅을 위해서 편집하기 위해 이동합니다.

    ```bash
    cd /etc/mysql/mysql.conf.d
    ```

5. 내용을 편집을 위해 다음 명령을 입력합니다.

    ```bash
    sudo vi mysqld.cnf
    ```

6. 그럼 vi 편집장이 등장하며 `a` 키를 눌러 INSERT MODE로 변경할 수 있습니다. 중앙부에 존재하는 `bind-address = 0.0.0.0`로 바꾸어줍니다.
7. `Esc`키를 눌러서 `:q` 명령어로 탈출합니다. 적용 후 세팅 값 변경을 위해 재시작합니다.

    ```bash
    sudo servie mysql restart
    ```

8. MySQL의 Basic 계정에 접속합니다. 기본적으로 세팅하지 않았다면 root의 password는 없어서 엔터로 접속 가능합니다. (혹시 접근이 안된다면 root의 비밀번호를 초기화해야 합니다.)

    ```bash
    sudo mysql -u root -p
    ```

9. root에서 작업하는 것은 적합하지 않기 때문에, 작업용 계정을 생성하고, 권한을 부여합니다. root의 SQL에서 다음을 입력합니다.

    ```bash
    CREATE USER 'new name'@'%' IDENTIFIED BY 'new password';
    GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
    FLUSH PRIVILEGES;
    ```

10. `exit`로 root 계정에서 나온 후, `sudo mysql -u <new-name> -p`를 사용해 9번에서 설정한 새로운 유저로 접속합니다. 그 후 root 계정에서 SQL을 정상적으로 입력할 수 있는 창이 제공된다면, 기본 세팅이 완료되게 됩니다.

## 🖥 Local MySQL WorkBench에서 연동하기

1. MySQL WorkBench를 열어서 새로운 내용을 추가하기 위해 '+' 버튼을 눌러줍니다.

    ![image](https://user-images.githubusercontent.com/45550607/128296941-8a027750-5c5a-4ec4-b16a-4a66a2e8dfce.png)

2. 다음을 방금 세팅한 EC2 내용대로 추가해줍니다.

    ![image](https://user-images.githubusercontent.com/45550607/128296963-b4772c63-1b78-4190-b9e5-babb384796aa.png)

3. 이후 `Test Connection` 버튼을 통해 `Success`를 확인한다면, `OK`로 완료해주시면 됩니다.