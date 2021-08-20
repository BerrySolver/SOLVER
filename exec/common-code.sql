-- common code
INSERT INTO `common_code` (`common_code`, `common_code_name`) VALUES ("000", "사용자"), ("001", "화상회의공개"), ("002", "회상회의참여"), ("003", "화상회의기록"), ("004", "질문상태"), ("005", "답변상태"), ("006", "알림종류"), ("007", "메시지"), ("008", "포인트기록"), ("009", "카테고리");

-- code
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("000", "000", "관리자", 1), ("001", "000", "일반사용자", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("010", "001", "공개", 1), ("011", "001", "비공개", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("020", "002", "질문자", 1), ("021", "002", "답변자", 1), ("022", "002", "참관자", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("030", "003", "입장", 1), ("031", "003", "퇴장", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("040", "004", "해결", 1), ("041", "004", "미해결", 1), ("042", "004", "만료", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("050", "005", "일반답변", 1), ("051", "005", "화상답변", 1), ("052", "005", "일반채택", 1), ("053", "005", "화상채택", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("060", "006", "답변알림", 1), ("061", "006", "댓글알림", 1), ("062", "006", "질문 좋아요 알림", 1), ("063", "006", "답변 좋아요 알림", 1), ("064", "006", "질문 북마크 알림", 1), ("065", "006", "답변 채택 알림", 1), ("066", "006", "팔로우 알림", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("070", "007", "일반읽음", 1), ("071", "007", "일반안읽음", 1), ("072", "007", "화상요청", 1), ("073", "007", "화상승인", 1), ("074", "007", "화상거절", 1), ("075", "007", "화상진행", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("080", "008", "얻은기록", 1), ("081", "008", "사용기록", 1);
INSERT INTO `code` (`code`, `common_code`, `code_name`, `use_yn`) VALUES ("090", "009", "초중고", 1), ("091", "009", "인문", 1), ("092", "009", "사회과학", 1), ("093", "009", "자연과학", 1), ("094", "009", "기술과학", 1), ("095", "009", "예술/체육", 1), ("096", "009", "기타", 1);

-- category
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("900", "090", "국어", 1), ("901", "090", "수학", 1), ("902", "090", "영어", 1), ("903", "090", "사회", 1), ("904", "090", "과학", 1), ("905", "090", "한국사", 1), ("906", "090", "제2외국어", 1);
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("910", "091", "문학", 1), ("911", "091", "역사", 1), ("912", "091", "언어", 1), ("913", "091", "철학", 1);
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("920", "092", "법", 1), ("921", "092", "경제", 1), ("922", "092", "교육", 1), ("923", "092", "사회", 1), ("924", "092", "통계", 1), ("925", "092", "행정", 1);
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("930", "093", "물리", 1), ("931", "093", "생명", 1), ("932", "093", "수학", 1), ("933", "093", "천문", 1), ("934", "093", "화학", 1), ("935", "093", "동/식물", 1);
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("940", "094", "건축", 1), ("941", "094", "농업", 1), ("942", "094", "기계공학", 1), ("943", "094", "화학공학", 1), ("944", "094", "전기/전자공학", 1), ("945", "094", "토목/환경공학", 1), ("946", "094", "컴퓨터/통신공학", 1);
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("950", "095", "미술", 1), ("951", "095", "음악", 1), ("952", "095", "사진/영상", 1), ("953", "095", "스포츠/건강", 1), ("954", "095", "공연/매체예술", 1);
INSERT INTO `category` (`sub_category_code`, `code`, `sub_category_name`, `use_yn`) VALUES ("960", "096", "기타", 1);

-- point code
INSERT INTO `point_code` (`point_code`, `code`, `value`, `point_name`, `user_yn`) VALUES ("000", "080", 30, "텍스트 답변 등록", 1), ("001", "080", 50, "화상 답변 등록", 1), ("002", "080", 50, "첫 답변", 1), ("003", "080", 50, "만료 12시간 이내 답변", 1), ("004", "080", 100, "텍스트 답변 채택", 1), ("005", "080", 200, "화상 답변 채택", 1), ("006", "080", 10, "질문 추천", 1), ("007", "080", 10, "답변 추천", 1), ("008", "080", 10, "질문 북마크 등록", 1);
INSERT INTO `point_code` (`point_code`, `code`, `value`, `point_name`, `user_yn`) VALUES ("100", "081", 100, "질문 신고", 1), ("101", "081", 100, "답변 신고", 1), ("102", "081", 100, "댓글 신고", 1), ("103", "081", 100, "1일 홍보", 1), ("104", "081", 200, "2일 홍보", 1), ("105", "081", 300, "3일 홍보", 1), ("106", "081", 100, "4일 홍보", 1), ("107", "081", 200, "5일 홍보", 1), ("108", "081", 300, "6일 홍보", 1), ("109", "081", 700, "7일 홍보", 1);