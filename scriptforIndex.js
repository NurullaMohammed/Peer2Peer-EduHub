// Photo upload functionality
const photoInput = document.getElementById("photo-input");
const photoCircle = document.getElementById("photo-circle");
const uploadText = document.getElementById("upload-text");
const profileImg = document.createElement("img");

profileImg.id = "profile-img";
profileImg.style.display = "none";
photoCircle.appendChild(profileImg);

photoCircle.addEventListener("click", () => {
  photoInput.click();
});

photoInput.addEventListener("change", function () {
  const file = this.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function () {
      profileImg.src = reader.result;
      profileImg.style.display = "block"; // Show the image
      uploadText.style.display = "none"; // Hide the upload text
    };
    reader.readAsDataURL(file);
  }
});

// Navigation between forms
const nextBtn = document.querySelector(".nextBtn");
const backBtn = document.querySelector(".backBtn");
const firstForm = document.querySelector(".form.first");
const secondForm = document.querySelector(".form.second");

nextBtn.addEventListener("click", () => {
  firstForm.classList.remove('active');
  secondForm.classList.add('active');
  document.body.classList.add('secActive'); // Ensure the class is applied to handle CSS transitions
});

backBtn.addEventListener("click", () => {
  secondForm.classList.remove('active');
  firstForm.classList.add('active');
  document.body.classList.remove('secActive'); // Ensure the class is removed to revert CSS transitions
});

// Handle subject selection
const dropdown = document.getElementById('subject-dropdown');
const selectedSubjectsContainer = document.querySelector('.selected-subjects');

dropdown.addEventListener('change', function () {
    const selectedSubject = dropdown.value;

    if (selectedSubject) {
        const subjectDiv = document.createElement('div');
        subjectDiv.innerHTML = `${selectedSubject} <button type="button" class="remove-btn">X</button>`;
        selectedSubjectsContainer.appendChild(subjectDiv);

        // Remove the subject from the dropdown
        dropdown.querySelector(`option[value="${selectedSubject}"]`).remove();

        // Add remove functionality
        subjectDiv.querySelector('.remove-btn').addEventListener('click', function () {
            subjectDiv.remove();
            dropdown.innerHTML += `<option value="${selectedSubject}">${selectedSubject}</option>`;
        });
    }
});
