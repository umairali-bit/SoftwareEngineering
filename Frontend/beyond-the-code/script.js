
const foldSlider = document.getElementById("fold");
const rotationSlider = document.getElementById("rotation");

const foldValue = document.getElementById("foldValue");
const rotationValue = document.getElementById("rotationValue");

const paper = document.getElementById("paper");
const mainFold = document.getElementById("mainFold");
const shadowFold = document.getElementById("shadowFold");

const resetButton = document.getElementById("reset");

function updateLogo() {

    const fold = Number(foldSlider.value);
    const rotation = Number(rotationSlider.value);

    // Update slider labels
    foldValue.textContent = fold;
    rotationValue.textContent = `${rotation}°`;

    // Main folded-paper shape
    const mainPath = `
        M 190 18
        L 45 80
        L 190 142
        L 190 ${142 - fold}
        L ${78 + fold} 80
        L 190 ${18 + fold}
        Z
    `;

    mainFold.setAttribute("d", mainPath);

    // Shaded section of the fold
    const shadowPath = `
        M 45 80
        L 190 ${18 + fold}
        L 190 ${25 + fold}
        L ${68 + fold} 84
        Z
    `;

    shadowFold.setAttribute("d", shadowPath);

    // Rotate around the SVG center
    paper.setAttribute(
        "transform",
        `rotate(${rotation} 120 80)`
    );
}

// Interactive controls
foldSlider.addEventListener("input", updateLogo);

rotationSlider.addEventListener("input", updateLogo);

// Reset to the original default state
resetButton.addEventListener("click", () => {

    foldSlider.value = 22;
    rotationSlider.value = 0;

    updateLogo();
});

// Initial render
updateLogo();
