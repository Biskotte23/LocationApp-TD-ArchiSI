/**
 * Fonction appelée lorsque l'utilisateur passe la souris
 * sur une location disponible.
 */
function mouseOnLocation(div) {
	div.style.backgroundColor = "lightblue";
}

/**
 * Fonction appelée lorsque l'utilisateur enlève la souris
 * d''une location disponible.
 */
function mouseOutLocation(div) {
	div.style.backgroundColor = "lightyellow";
}

/**
 * Fonction appelée lorsque l'utilisateur clique sur une
 * location disponible.
 */
function submitForm(div) {
	div.form.submit();
}