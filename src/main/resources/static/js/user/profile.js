const profileImgElem = document.querySelector('#flexContainer .profile.w300.pointer');
const modalElem = document.querySelector('.modal');
const modalCloseElem = document.querySelector('.modal .modal_close');
console.log(modalElem);
//모든 no 아이콘에 이벤트를 걸어준다 그래서 list랑 queryselectorall썼음
const noMainProfileList = document.querySelectorAll('.no-main-profile');
const profileImgParentList = document.querySelectorAll('.profile-img-parent');

//모달창 띄우기
profileImgElem.addEventListener('click', () =>{
    modalElem.classList.remove('hide');
});

//모달창 닫기
modalCloseElem.addEventListener('click', ()=>{
    modalElem.classList.add('hide');
});

//모든 no 아이콘에 이벤트를 걸어준다 그래서 list랑 queryselectorall썼음
//이벤트는 메인 이미지 변경처리
noMainProfileList.forEach((item) =>{
    item.addEventListener('click', ()=>{
        const iprofile = item.dataset.iprofile;
        changeMainProfile(iprofile);
        console.log(iprofile);
    });
});

//메인 이미지 변경
function changeMainProfile(iprofile){
    fetch(`/user/mainProfile?iprofile=${iprofile}`)
        .then(res => res.json())
        .then(myJson =>{
            console.log(myJson.result);
        });
}