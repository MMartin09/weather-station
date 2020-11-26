help:
	$(info * doc_pdf: Build the documentation as pdf)
	$(info * api_html: Build the api documentation as html)
	$(info * ktlint: Run ktlint)

doc_pdf:
	cd .\doc\documentation && asciidoctor-pdf -r asciidoctor-diagram documentation.adoc

api_html:
	cd .\weather-station && .\gradlew dokkaHtml

ktlint:
	cd .\weather-station && .\gradlew ktlintCheck
