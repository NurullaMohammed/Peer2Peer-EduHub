document.addEventListener('DOMContentLoaded', function () {
    
    const successPopup = document.getElementById('successPopup');
    const closePopup = document.getElementById('closePopup');

        successPopup.style.display = 'block';

    closePopup.addEventListener('click', function () {
        successPopup.style.display = 'none';
    });

    // Close the popup when the user clicks outside of it
    window.addEventListener('click', function (event) {
        if (event.target === successPopup) {
            successPopup.style.display = 'none';
        }
    });
});
