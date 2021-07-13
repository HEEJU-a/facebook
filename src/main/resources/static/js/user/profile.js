const modalElem = document.querySelector('section .modal');
const modalCloseElem = document.querySelector('section .modal .modal_close');
const btnFollowElem = document.querySelector('#btnFollow'); //팔로우 버튼
const profileImgElem = document.querySelector('#profileImg');


//모달창 띄우기 이벤트
profileImgElem.addEventListener('click', () => {
    modalElem.classList.remove('hide');
});

//모든 no-main-profile 아이콘에 이벤트를 걸어준다.
//이벤트는 메인 이미지 변경처리
const profileImgParentList = document.querySelectorAll('.profile-img-parent');
profileImgParentList.forEach(item => {
    const iElem = item.querySelector('i');
    if(iElem != null) {
        addIElemEvent(iElem);
    }
});

//메인이미지 바꾸기 아이콘에 이벤트 설정
function addIElemEvent(target) {
    target.addEventListener('click', () => {
        const iprofile = target.parentNode.dataset.iprofile;
        console.log(iprofile);
        changeMainProfile(iprofile);
    });
}

//메인 이미지 변경
function changeMainProfile(iprofile) {
    fetch(`/user/mainProfile?iprofile=${iprofile}`)
        .then(res => res.json())
        .then(myJson => {
            switch(myJson.result) {
                case 0:
                    alert('메인 이미지 변경에 실패하였습니다.'); break;
                case 1:
                    setMainProfileIcon(iprofile);

                    //section에 있는 프로필 이미지 변경
                    const src = profileImgElem.src;
                    const frontSrc = src.substring(0, src.lastIndexOf("/"));
                    const resultSrc = `${frontSrc}/${myJson.img}`
                    profileImgElem.src = resultSrc;

                    //헤더에 있는 프로필 이미지 변경
                    const headerProfileImgElem = document.querySelector('header .span__profile img');
                    headerProfileImgElem.src = resultSrc;
                    break;
            }
        });
}
//현재 mainProfile로 바뀐 iprofile값 입니다.
function setMainProfileIcon(iprofile) {
    profileImgParentList.forEach(item => {
        item.innerHTML = '';
        const itemIprofile = item.dataset.iprofile;
        if(iprofile !== itemIprofile) {
            const iElem = document.createElement('i');
            iElem.className = 'no-main-profile pointer fas fa-bell';
            item.append(iElem);
            addIElemEvent(iElem);
        }
    });
}

//모달창 닫기
if(modalCloseElem) {
    modalCloseElem.addEventListener('click', () => {
        modalElem.classList.add('hide');
        //location.reload();
    });
}
//팔로우 처리
//type: 0 > 팔로우 처리
//type: 1 > 팔로우 취소
function followProc(type, iuserYou, btnElem) {
    const init = {};
    const param = { iuserYou };
    let queryString = '';
    switch (type) {
        case 0:
            init.method = 'POST';
            init.headers = { 'Content-Type': 'application/json' };
            init.body = JSON.stringify(param);
            break;
        case 1:
            init.method = 'DELETE';
            queryString = `?iuserYou=${iuserYou}`;
            break;
    }

    fetch('follow' + queryString, init)
        .then(res => res.json())
        .then(myJson => {

            if(myJson.result === 1) {
                let buttnNm = '팔로우 취소';
                if(btnElem.dataset.follow === '1') {
                    if(myJson.youFollowMe == null) { buttnNm = '팔로우'; }
                    else { buttnNm = '맞팔로우'; }
                }
                btnElem.classList.toggle('instaBtnEnable');
                btnElem.value = buttnNm;
                btnElem.dataset.follow = 1 - btnElem.dataset.follow;
            } else {
                alert('에러가 발생하였습니다.');
            }
        });
}

if(btnFollowElem) {
    btnFollowElem.addEventListener('click', () => {
        const follow = parseInt(btnFollowElem.dataset.follow);
        const iuser = localConstElem.dataset.iuser;

        followProc(follow, iuser, btnFollowElem);
    });
}


const localConstElem = document.querySelector('#localConst');

feedObj.url = '/user/feedList';
feedObj.setScrollInfinity(window);
feedObj.iuser = localConstElem.dataset.iuser;
feedObj.getFeedList(1);


/******************************************************** 팔로우 모달 **/
const followerElemArr = document.querySelectorAll('.pointer.follower');
const followElemArr = document.querySelectorAll('.pointer.follow');
const modalFollowElem = document.querySelector('.modal-follow');
const modalFollowTitleElem = modalFollowElem.querySelector('#title');
const modalFollowCloseElem = document.querySelector('.modal-follow #modal-follow-close');
const modalFollowItemConElem = modalFollowElem.querySelector('.followCont');

if(followerElemArr) {
    followerElemArr.forEach(item => {
        item.addEventListener('click', () => {
            modalFollowTitleElem.innerText = '팔로워';
            modalFollowElem.classList.remove('hide');
            modalFollowItemConElem.innerHTML = '';

            //프로필 사용자를 팔로우한 사람들 리스트
            fetch(`getFollowerList?iuserYou=${localConstElem.dataset.iuser}`)
                .then(res => res.json())
                .then(myJson => {
                    if(myJson.length > 0) {
                        myJson.forEach(item => {
                            const cont = makeFollowItem(item);
                            modalFollowItemConElem.append(cont);
                        });
                    }
                });
        });
    });
}

if(followElemArr) {
    followElemArr.forEach(item => {
        item.addEventListener('click', () => {
            modalFollowTitleElem.innerText = '팔로우';
            modalFollowElem.classList.remove('hide');
            modalFollowItemConElem.innerHTML = '';

            //프로필 사용자가 팔로우한 사람들 리스트
            fetch(`getFollowList?iuserYou=${localConstElem.dataset.iuser}`)
                .then(res => res.json())
                .then(myJson => {
                    if(myJson.length > 0) {
                        myJson.forEach(item => {
                            const cont = makeFollowItem(item);
                            modalFollowItemConElem.append(cont);
                        });
                    }
                });
        });
    });
}

if(modalFollowCloseElem) {
    modalFollowCloseElem.addEventListener('click', () => {
        modalFollowElem.classList.add('hide');
    });
}

function makeFollowItem(item) {
    const globalContElem = document.querySelector('#globalConst');
    const loginIuser = globalContElem.dataset.iuser;

    const cont = document.createElement('div');
    cont.className = 'follow-item';
    const img = document.createElement('img');
    img.className = 'profile wh30 pointer';
    img.src = `/pic/profile/${item.iuser}/${item.mainProfile}`;
    img.onerror = () => { img.style.visibility = 'hidden'; }
    img.addEventListener('click', ()=> {
        moveToProfile(item.iuser); //feed.js 에 있는 메소드
    });

    const nm = document.createElement('div');
    const nmText = document.createElement('span');
    nmText.innerText = item.nm;
    nmText.className = 'pointer';
    nmText.addEventListener('click', ()=> {
        moveToProfile(item.iuser); //feed.js 에 있는 메소드
    });
    nm.append(nmText);

    const btn = document.createElement('input');
    btn.className = 'instaBtn pointer';
    btn.dataset.follow = '0';
    btn.addEventListener('click', () => {
        const follow = parseInt(btn.dataset.follow);
        followProc(follow, item.iuser, btn);
    });

    cont.append(img);
    cont.append(nm);
    if(parseInt(loginIuser) !== item.iuser) {
        btn.type = 'button';
        if(item.isMeFollowYou) {
            btn.dataset.follow = '1';
            btn.value = '팔로우 취소';
        } else {
            btn.classList.add('instaBtnEnable');
            btn.value = '팔로우';
        }
        cont.append(btn);
    }
    return cont;
}