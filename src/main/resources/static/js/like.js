document.addEventListener('DOMContentLoaded', function () {
    const likeSection = document.getElementById('like-section');
    if (!likeSection) return;

    const questionId = likeSection.getAttribute('data-question-id');
    const likeCountSpan = document.getElementById('like-count');
    const likeBtn = document.getElementById('like-btn');
    const unlikeBtn = document.getElementById('unlike-btn');

    function updateLikeUI(isLiked, count) {
        likeCountSpan.textContent = count;
        if (isLiked) {
            if (likeBtn) likeBtn.style.display = 'none';
            if (unlikeBtn) unlikeBtn.style.display = '';
        } else {
            if (likeBtn) likeBtn.style.display = '';
            if (unlikeBtn) unlikeBtn.style.display = 'none';
        }
    }

    if (likeBtn) {
        likeBtn.addEventListener('click', function () {
            fetch(`/api/likes/question/${questionId}`, {
                method: 'POST',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'X-CSRF-TOKEN': getCsrfToken(),
                }
            }).then(r => {
                if (r.status === 401) { alert('Войдите для лайка!'); return; }
                if (!r.ok) throw new Error(r.statusText);
                return r.json();
            }).then(count => {
                if (count !== undefined) updateLikeUI(true, count);
            });
        });
    }

    if (unlikeBtn) {
        unlikeBtn.addEventListener('click', function () {
            fetch(`/api/likes/question/${questionId}`, {
                method: 'DELETE',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest',
                    'X-CSRF-TOKEN': getCsrfToken(),
                }
            }).then(r => {
                if (r.status === 401) { alert('Войдите для дизлайка!'); return; }
                if (!r.ok) throw new Error(r.statusText);
                return r.json();
            }).then(count => {
                if (count !== undefined) updateLikeUI(false, count);
            });
        });
    }

    function getCsrfToken() {
        const meta = document.querySelector('meta[name="_csrf"]');
        return meta ? meta.content : '';
    }
});