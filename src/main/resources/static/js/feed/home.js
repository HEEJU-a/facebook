const feedContainerElem = document.querySelector('#feedContainer');

//피드 리스트 가져오기
function getFeedList() {
    fetch('list')
        .then(res => res.json())
        .then(myJson => {
            console.log(myJson);
            makeFeedList(myJson);
        });
}

function makeFeedList(data) {
    if(data.length == 0) { return; }
    let beforeifeed = 0;
    let swiperWrapperDiv = null;
    for(let i=0; i<data.length; i++) {
        const item = data[i];

        if(beforeifeed !== item.ifeed) {//예를 들어 3번ifeed에서 2번ifeed로 바뀌면 새로운 피드 시작이다! 라는걸 알수있음
            beforeifeed = item.ifeed;

            const itemContainer = document.createElement('div');
            itemContainer.classList.add('item');

            const topDiv = document.createElement('div');
            topDiv.classList.add('top')
            topDiv.innerHTML = `
            <div class="itemProfileCont">
                <img src="/pic/profile/${item.iuser}/${item.mainProfile}">
            </div>
            <div>
                <div>${item.writer}</div>
                <div>${item.location == null ? '' : item.location}</div>
            </div>
            `;
            const imgDiv = document.createElement('div');
            imgDiv.classList.add('itemImg');

            const swiperContainerDiv = document.createElement('div');
            swiperContainerDiv.classList.add('swiper-container');

            swiperWrapperDiv = document.createElement('div');
            swiperWrapperDiv.classList.add('swiper-wrapper');

            swiperContainerDiv.append(swiperWrapperDiv);
            imgDiv.append(swiperContainerDiv);

            itemContainer.append(topDiv);
            itemContainer.append(imgDiv);
            if(item.ctnt != null) {
                const ctntDiv = document.createElement('div');
                ctntDiv.innerText = item.ctnt;
                ctntDiv.classList.add('itemCtnt');
                itemContainer.append(ctntDiv);
            }
            feedContainerElem.append(itemContainer);
        }

        if(item.img != null) {
            const swiperSlideDiv = document.createElement('div')
            swiperSlideDiv.classList.add('swiper-slide');

            const img = document.createElement('img');
            img.src = `/pic/feed/${item.ifeed}/${item.img}`;
            swiperSlideDiv.append(img);
            swiperWrapperDiv.append(swiperSlideDiv);
        }
    }

    const swiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: false,
    });
}

getFeedList();