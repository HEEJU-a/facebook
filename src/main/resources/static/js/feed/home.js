const feedContainerElem = document.querySelector('#feedContainer');

//피드리스트 가져오기
function getFeedList(){
    fetch('list')
        .then(res => res.json())
        .then(myJson => {
            //console.log(myJson);
            makeFeedList(myJson);
        });
}

function makeFeedList(data){
    if(data.length == 0){return;}
    let beforeifeed = 0;
    let imgDiv = null;
    data.forEach(item=>{
        if(beforeifeed !== item.ifeed){
            beforeifeed = item.ifeed;


            const itemContainer = document.createElement('div');//사진 담고 있는 제일 큰 div
            itemContainer.classList.add('item');

            const topDiv = document.createElement('div'); // 큰 div의 가장 위에 있는 div
            topDiv.classList.add('top');
            topDiv.innerHTML = `
            <div class="itemProfileCont">
                <img src="/pic/profile/${item.iuser}/${item.mainProfile}">
            </div>
            <div>
                <div>${item.writer}</div>
                <div>${item.location == null ? '' : item.location}</div>           
            </div>
        `;
            //이미지 넣기 for문 돌면서 계속 추가 될수있어야함
            imgDiv = document.createElement('div');

            itemContainer.append(topDiv);
            itemContainer.append(imgDiv);
            feedContainerElem.append(itemContainer);
        }

        //DB에서 img 부터는 if문 끝난뒤에
        if(item.img != null){
            const img = document.createElement('img');
            img.src= `/pic/feed/${item.ifeed}/${item.img}`;
            imgDiv.append(img);
        }
    });
    // 여기서는 반복문 돌리면서 피드를 뿌릴것임
}
getFeedList();
