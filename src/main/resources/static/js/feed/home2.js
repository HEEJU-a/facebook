feedObj.containerElem = document.querySelector('#feedContainer');
feedObj.loadingElem = document.querySelector('.loading');
feedObj.url = '/feed/list2';
feedObj.setScrollInfinity(window);
feedObj.getFeedList(1);