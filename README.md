# android-swipable-cardview

<img src="https://github.com/jangyoun/android-swipable-cardview/raw/master/preview.gif" width="250">

##Description
 - Using [AndroidSwipeableCardStack Lib] (https://github.com/wenchaojiang/AndroidSwipeableCardStack)
 - Using [http JSON DATA] (http://leejangyoun.com/android/dummy/SlideCardView_1.json)

 - AndroidSwipeableCardStack Lib의 내부 소스를 수정하여 사용
    > 변경 사항 1 : 카드의 크기를 조절을 위해 마진 값을 변경
    > 변경 사항 2 : 카드 스와이프 중 크기가 변경되어 레이아웃의 배열이 변경되어, Width 값을 고정하여 배열을 유지 함

 - HTTP 통신
   > son Array 갯수 만큼 CardStack을 추가함
   > page 적용 : json data의 last 인자를 통해, 마지막 페이지를 인지함
   > card를 소모하였을 경우, HTTP reload 함

 - 기타 
   > http 통신의 경우, volley stringrequest 사용함
   > glide image loader 사용함
