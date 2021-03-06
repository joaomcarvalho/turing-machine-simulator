// Turing Machine Simulator Page Functionalities
// Author: Clara Moraes Dantas
// Contributor: Igor Brasileiro

var numberBox = document.getElementById('numbers'),
	textBox = document.getElementById('source'),
	textAreaLines = -1;

var firstClickInputArea = true,
	inputAreaText = document.getElementById('inputAreaT');

function textAreaChanged()
{
	var newLines = (textBox.value.match(/\n/g) ? textBox.value.match(/\n/g).length : 0) + 1;

	if (newLines != textAreaLines) {
		textAreaLines = newLines;
		addLine();
	}
}

function addLine()
{
	var textBoxValue = textBox.value;
	textBox.value.replace( /\r/g, "");

	var lines = textBox.value.split("\n").length;

	var n = 1, holder = '';

	for (n = 1; n <= lines; n++) {
		holder += '<div class="row">' + n + '.</div>';
	}

	if (lines === 0) {
		holder = '<div class="row">1.</div>';
	}

	numberBox.innerHTML = holder;
	updateScroll();
}

function updateScroll()
{
	var numbers = $("#numbers");
	$("#source").scroll(function() {
		numbers.prop("scrollTop", this.scrollTop);
	});
}

function blackFont()
{
	inputAreaText.style.color = "black";
}


