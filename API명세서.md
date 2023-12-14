## Api명세서는 블로그 검색하기와 인기검색어top10을 정의했습니다


---

## 1.블로그 검색하기

### `기본정보`

|||
|:--|:--|
|`메서드`|`URL`|
|GET|http://localhost:8080/search|

다음 블로그, 네이버 블로그 서비스에서 질의어 검색할 수 있습니다.

원하는 검색어와 함께 결과 형식 파라미터를 선택적으로 추가할 수 있습니다. 


### `쿼리 파라미터`

|||||
|:--:|:--:|:--:|:--:|
|`이름`|`타입`|`설명`|`필수`|
|query|String|검색 질의어 입니다|O|
|sort|String|결과 문서 정렬 방식, accuracy(정확도순) 또는 <br/>recency(최신순), 기본 값 accuracy|X|
|page|Integer|결과 페이지 번호, 1~40 사이의 값. 기본값은 1<br/> 음수나 0이들어오면 400 badrequest처리|X|
|size|Integer|한 페이지에 보여질 문서 수, 1~30 사이의 값. 기본값은 10|X|
|domain|String|어느 블로그 도메인을 검색할지에 대한 값 <br/> naver, kakao 등이 들어갈 수 있으며 기본값은 kakao<br/> 대소문자 상관없이 처리|X|

### `응답`

#### 본문

||||
|:--:|:--:|:--:|
|`이름`|`타입`|`설명`|
|totalCount|Integer|검색된 문서 수|
|displayCount|Integer|요청 후 보여진 응답 블로그 문서의 수|
|isEnd|Boolean|현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜<br/> 다음 페이지를 요청할 수 있음|
|domain|String|어느 블로그 검색엔진인지 표시하는 도메인 ex) NAVER, KAKAO|
|pageableCount|Integer|totalCount중 노출 가능 문서수(카카오 블로그 검색시에만 노출)|
|content|Content|응답결과|

#### Content

`카카오 검색의 경우`
||||
|:--:|:--:|:--:|
|`이름`|`타입`|`설명`|
|title|String|블로그 글 제목|
|contents|String|블로그 글 요약|
|url|String|블로그 글 URL|
|blogname|String|블로그의 이름|
|thumbnail|String|검색 시스템에서 추출한 대표 미리보기 이미지 URL,<br/> 미리보기 크기 및 화질은 변경될 수 있음|
|datetime|Datetime|블로그 글 작성시간, ISO 8601<br/>[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]|

`네이버 검색의 경우`

||||
|:--:|:--:|:--:|
|`이름`|`타입`|`설명`|
|title|String|블로그 글 제목|
|description|String|블로그 글 요약|
|link|String|블로그 글 URL|
|bloggername|String|블로그 포스트가 있는 블로그의 이름|
|bloggerlink|String|블로그 포스트가 있는 블로그의 주소|
|postdate|dateTime|블로그 포스트가 작성된 날짜|

요청 url 예시1
```
http://localhost:8080/search?query=카카오 주식&size=2&page=3&sort=accuracy
```

응답

```json
{
    "content": [
        {
            "title": "[디지털 마케팅] <b>카카오</b>페이 고구마줄게 <b>주식</b>다오3 이벤트 사례 (금융, 핀테크)",
            "contents": "2018년 부터 시작된 해외<b>주식</b> 소수점 거래로 인해 적은 돈으로도 해외<b>주식</b>을 구매할 수 있다는 거래의 용이성도 한몫했다고 생각한다. 아래는 <b>카카오</b>페이의 고구마줄게 <b>주식</b>다오 이벤트 시즌 3에 대한 이벤트 사례 소개이다. 목차 이벤트 개요 이벤트 둘러보기 &lt;<b>카카오</b>페이고구마줄게<b>주식</b>다오3&gt; 디지털 마케팅 이벤트에...",
            "url": "http://branding-space.tistory.com/125",
            "blogname": "Branding_space",
            "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/H9nGOBgqjjS",
            "datetime": "2023-06-24T10:03:35.000+09:00"
        },
        {
            "title": "<b>카카오</b> <b>주식</b> 맞히고 <b>주식</b> 받기 _ 절반정도 맞히면 얼마나 줄까?",
            "contents": "이벤트를 진행하는 것을 보니 이벤트 반응이 좀 괜찮았나 봐요. 오늘은 <b>카카오</b> <b>주식</b> 맞히는 이벤트에 대해 알아보도록 하겠습니다. <b>카카오</b> <b>주식</b> 이벤트 참여하는 방법 <b>카카오</b> 페이 앱 ▶ 하단 메뉴 <b>주식</b> ▶ 가운데 이벤트 배너 ▶ 원하는 <b>주식</b> 선택 ▶ 오른다 / 내린다 선택 이렇게 간단하게 이벤트에 참여할 수가...",
            "url": "http://jalsalabose90.tistory.com/259",
            "blogname": "경제보는 비둘기",
            "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/IJIyfYjMG45",
            "datetime": "2023-03-31T20:37:51.000+09:00"
        }
    ],
    "totalCount": 632635,
    "pageableCount": 799,
    "displayCount": 2,
    "isEnd": false,
    "domain": "KAKAO"
}
```




요청 url 예시2
```
http://localhost:8080/search?query=뉴진스&size=1&domain=naver&sort=recency
```

응답
```json
{
    "content": [
        {
            "title": "[해피 마이너 이슈] 요즘 드라마 속 재벌 모습.gif",
            "link": "https://blog.naver.com/cocoshoo80/223146961362",
            "description": "[해피 마이너 이슈] 요즘 드라마 속 재벌 모습.gif[해피 마이너 이슈] 요즘 드라마 속 재벌 모습.gif[해피 마이너 이슈] 요즘 드라마 속 재벌 모습.gif 댓 글 0 간 편 로 그 인 하 고 댓 글 작 성 카 카 오 계... ",
            "bloggername": "해피 마이너 이슈",
            "bloggerlink": "blog.naver.com/cocoshoo80",
            "postdate": "20230704"
        }
    ],
    "totalCount": 2082733,
    "displayCount": 1,
    "isEnd": false,
    "domain": "NAVER"
}
```





## 2. 최근 인기검색어 top10 조회하기

|||
|:--|:--|
|`메서드`|`URL`|
|GET|http://localhost:8080/popular|

최근 인기검색어 10개를 나타내줍니다

아무것도 검색한 기록이 없을 시에는 빈 리스트를 반환합니다

요청 예시1 (검색한 기록이 있을 때)
```
http://localhost:8080/popular
```

응답

```json
[
    {
        "keyword": "카카오 주식",
        "count": 6
    },
    {
        "keyword": "뉴진스",
        "count": 1
    },
    {
        "keyword": "스",
        "count": 1
    },
    {
        "keyword": "wls스",
        "count": 1
    },
    {
        "keyword": "뉴스",
        "count": 1
    }
]
```

요청 예시 2 (한번도 검색을 하지 않았을 때)
```
http://localhost:8080/popular
```

응답

```json
[]
```
