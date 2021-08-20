# 솔버(Solver)

![image](https://user-images.githubusercontent.com/45550607/127493916-e2f4d237-272a-439f-903c-a66ea4261788.png)

**당신은 특별하다. 당신의 질문과 답변도 특별하다.**
> 사소하지만 나에겐 어려운 문제를 해결하지 못해 답답했던 적 있으신가요? 혹은 하나 때문에 막혀서 진도가 안가는 경우는 있으셨나요?<br><br>
> 언제든 내 질문을 해결하고, 실시간으로 맞춤형 답변을 얻어보세요!<br>
> 그렇게 성장한 여러분은 다른 이의 고민을 해결해주며 경력 쌓기를 시작할 수 있습니다. <br><br>
> 누구든 해결사를 찾고, 해결사가 되어보세요.

<br><br>

## 프로젝트 목차
- [솔버(Solver)](#솔버solver)
	- [프로젝트 목차](#프로젝트-목차)
	- [1️⃣ 프로젝트 소개](#1️⃣-프로젝트-소개)
		- [📋 기술 스택](#-기술-스택)
		- [🎨 디자인](#-디자인)
	- [2️⃣ 프로젝트 파일 구조](#2️⃣-프로젝트-파일-구조)
		- [Back](#back)
		- [Front](#front)
	- [4️⃣ 프로젝트 산출물](#4️⃣-프로젝트-산출물)
	- [5️⃣ 프로젝트 결과물](#5️⃣-프로젝트-결과물)

- [📺 프로젝트 UCC](https://drive.google.com/file/d/12Mfx3sRjNwTiT2undbCMqLwqhOcUjI2W/view?usp=sharing)
<br><br>

## 1️⃣ 프로젝트 소개

1. 일정 : 2021-07-12 ~ 2021-08-20 (총 6주)
   - Sub1 : 2021-07-12 ~ 2021-07-16
   - Sub2 : 2021-07-19 ~ 2021-07-30
   - Sub3 : 2021-08-02 ~ 2021-08-20 (🔥 NOW 🔥)
2. 인원 (총 4인)
	 - 나승호 : Back-end, Front-end
	 - 당현아 : 팀장, Back-end, Front-end, AWS EC2, Design
	 - 박나영 : Front-end, Design
	 - 조현민 : 부팀장, Back-end, Front-end, AWS EC2

<br><br>

### 📋 기술 스택

1. 이슈관리 : Jira
2. 형상관리 : Gitlab
3. 커뮤니케이션 : Mattetmost, Notion
4. 개발 환경
	- OS : Windows 10
	- IDE
		- Spring Tool Suite 3.9.14 
		- Visual Studio Code 1.58
		- UI/UX : Adobe Photoshop, Figma
	- Database : MySQL Workbench 8.0.22
	- Server : AWS EC2 (MobaXterm)
		- Ubuntu 20.04.2 LTS
		- Docker 20.10.7
5. 상세 사용
	- Backend
		- Java (Open-JDK aulu 8.33.0.1)
		- Spring Boot Gradle 7.2
		- lombok 1.18.20, Kurento 2.0, swagger2 3.0.0, Querydsl-Jpa
	- Frontend
		- HTML5, CSS3, JavaScript(ES6)
		- Vue 2.6.11, Vuex 3.4.0
		- Node.js 14.17.3
		- ckeditor5 1.0.5, chart.js 3.5.1, kurento 6.16.0, js-modal 2.0.1, randomcolor 1.0.4
	- AWS S3 1.12.39

<br>

### 🎨 디자인
- [Notion에서 크게보기](https://www.notion.so/danghyeona/6369d09c85a243a299d7402a2e4d8944)

![image](https://user-images.githubusercontent.com/45550607/127514001-020a11cc-e660-476f-8a09-7137fdec219f.png)

<br><br>

## 2️⃣ 프로젝트 파일 구조

### Back

```
com
└── solver
	├── api
	│   ├── controller
	│   ├── service
	│		├── request
	│   └── response
	│
	├── common
	│   ├── util
	│   ├── exception
	│   ├── model
	│   └── auth
	│
	├── config
	│
	└── db
			├── entity
			└── repository
```

### Front

```
solver-frontend
├── node_modules
├── public
└── src
	├── assets
     	├── router
	├── components
		├── groups
		├── main
		├── profiles
		└── questions
	├── router
	├── store
		└── modules
	└── views
		├── auth
		├── groups
		├── main
		├── notifications
		├── profiles
		├── questions
		├── reports
		└── solvers
```

<br><br>

## 4️⃣ 프로젝트 산출물
> 프로젝트 기간 중 꾸준히 업데이트하는 목록입니다.
- [프로젝트 메모 및 공유 : Notion](https://danghyeona.notion.site/Solver-1b2691348e2d4334a4deb5699167ef59)
- [프로젝트 회의록](./docs/회의록.md)

<br>

- [프로젝트 컨벤션 목록](./docs/컨벤션목록.md)
- [프로젝트 스토리보드 및 디자인](./docs/디자인스토리보드.md)
- [기획서](./docs/기획서.md)
- [API Docs](https://www.notion.so/danghyeona/API-961534ee05374f65ad6fb75d55167f65)
- [와이어 프레임](./docs/와이어프레임.md)
- [서비스 아키텍처](./docs/아키텍처.md)
- [요구사항 명세서](./docs/요구사항명세서.md)
- [데이터베이스:ERD](./docs/데이터베이스.md)
- [시퀀스 다이어그램](./docs/시퀀스다이어그램.md)
- [Git 협업](./docs/Git-관리.md)
- [Jira 이슈 관리](./docs/Jira-이슈-관리.md)
- [AWS EC2 환경 설정](./docs/AWS-EC2.md)
- [공통코드-메타데이터](./docs/공통코드-메타데이터.md)


<br><br>

## 5️⃣ 프로젝트 결과물
> 프로젝트 종류 산출물입니다.

- [포팅메뉴얼](./exec/공통PJT_서울5반_A507_포팅메뉴얼.pdf)
- [시연시나리오](./exec/공통_PJT_서울5반_A507_시연시나리오.pdf)
- [중간발표자료](./pt/[중간]%20공통_PJT_서울5반_A507.pdf)
- [최종발표자료](./pt/[최종]%20공통_PJT_서울5반_A507.pdf)
