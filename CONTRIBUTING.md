# Contributing Guide

Tak fordi du vil bidrage til projektet 🙌  
For at gøre samarbejdet nemt og overskueligt, så følger vi nogle simple retningslinjer.

---

## 1. Issues & branches

1. Gå til projektet på GitHub og find et issue, du vil arbejde på.
2. **Tildel dig selv issuet** (assign), så andre kan se, at du er på den.
3. Opret en ny branch med et sigende navn, fx:
    - `feature/login-form`
    - `bugfix/fix-null-pointer`
    - `chore/update-dependencies`

Eksempel:


```
git checkout main 
git pull
git checkout -b feature/beskrivende-branch-navn
```

---

## 2. Commits

Vi vil gerne have små, meningsfulde commits med beskrivende commit-beskeder.
- Skriv hvad du har lavet.
- Skriv gerne på engelsk eller dansk, bare det er forståeligt og konsistent.
- Tænk: “Kan en anden forstå hvad jeg ændrede, kun ud fra commit-beskeden?”

Eksempler på gode commit-beskeder:

```
git commit -m "Tilføj validering på loginform"
git commit -m "Ret bug hvor tomt felt crashede siden"
git commit -m "Opdater README med installationsvejledning"
```

---

## 3. Hold din branch opdateret (merge fra main)

Inden du laver en pull request, skal din branch være opdateret med main, 
og merge conflicts skal være løst på din egen branch.

#### Følg denne proces:
```
# Gå til main og hent seneste ændringer
git checkout main
git pull

# Gå tilbage til din branch
git checkout din-branch

# Merge main ind i din branch
git merge main
```
#### Hvis der opstår merge conflicts:
1.	Åbn de filer med conflicts.
2.	Løs konflikterne manuelt.
3.	Når alle conflicts er løst:

```
git add .
git commit
git push
```
Når din branch er opdateret, kan du lave en pull request.

--- 

## 4. Pull Requests

Når du er klar til at få din kode gennemgået:
1.	Push din branch til GitHub:
```
git push -u origin din-branch
```
2.	Opret en Pull Request (PR) mod main.
3.	I PR-beskrivelsen:
   - Beskriv kort hvad du har lavet.
   - Referer til relevante issues, fx:
   Closes #12 eller Relates to #34.

Eksempel på PR-beskrivelse:

```
Denne PR tilføjer validering på loginform:
- Tjekker tomme felter
- Viser fejlbesked til brugeren
- Tilføjer simple enhedstests

Closes #12
```
---
## 5. Code review & feedback

Vi laver altid review på hinandens pull requests.

Som reviewer:
1.	Læs koden og tjek:
   - Er den forståelig og konsistent?
   - Følger den projektets stil/struktur?
2.	Giv feedback, hvis:
   - Koden er unødigt kompleks.
   - Der mangler tests.
   - Der er oplagte forbedringer i navngivning, struktur eller performance.
3.	Brug GitHubs kommentarfunktion til konkrete linjer i koden.
4.	Husk at være konstruktiv og respektfuld – målet er bedre kode, ikke at kritisere personen.

--- 

## 6. Test & kvalitetssikring

Som reviewer (og gerne også før du opretter PR):
1.	Check branchen ud lokalt:

```
git fetch
git checkout din-kollegas-branch
```

2.	Kør relevante tests / appen:
   - For at der ikke er skabt nye bugs.
   - Test de ændrede funktioner manuelt, hvis det giver mening.
3.	Hvis du finder noget:
   - Kommentér det i PR’en.
   - Beskriv, hvad du gjorde, og hvad der gik galt.

Når alt ser godt ud:
- Godkend PR’en (Approve).
- Merge efter aftalt praksis (fx “Squash and merge” eller “Merge commit”).

--- 
