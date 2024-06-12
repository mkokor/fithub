<p align="center">
  <img width='500px' height='170px' src='logo.png' alt='icon' style='margin: auto;'>
</p>
<h1 align='center'>Fithub</h1>

# 🧑‍💻 Demo aplikacije

Demo videa možete pogledati na sljedećem linku: https://drive.google.com/drive/folders/1VH3lrnldw_NaWt_wrzH-lZRlUkdpj-oe

# 🏋️‍♂️ Šta je FitHub

FitHub je full-stack aplikacija razvijena od strane studenata Elektrotehničkog fakulteta u Sarajevu:

- https://github.com/mkokor
- https://github.com/nkokor
- https://github.com/nhelac1
- https://github.com/mbesirevic1


Aplikacija je razvijena u sklopu predmeta Napredne web tehnologije s idejom olakšanja poslovanja teretane. 
Za razvoj aplikacije korišteni su Spring Boot za backend i React za frontend.


# 💡 Napomena

Frontend i backend projekti inicijalno su se nalazili na različitim repozitorijima. Razvoj frontenda prije prebacivanja u centralni repozitorij možete vidjeti prateći sljedeći link: https://github.com/nkokor/fithub


# 🦾 Funkcionalnosti
FitHub osim osnovne funkcionalnosti registracije korisnika uključuje i

- online izbor trenera
- pregled i uvid u najnovije vijesti i ponude koje teretana nudi svojim korisnicima
- pregled personaliziranog sedmičnog plana ishrana i preuzimanje istog u obliku pdf dokumenta (za klijenta)
- ažuriranje sedmičnog plana ishrane (za trenera)
- uvid u termine grupnih treninga i prijava na slobodne termine (za klijenta)
- upućivanje glazbenih prijedloga za termine (za klijenta)
- pregled najuspješnijih klijenata
- uvid u vlastite rezultate i preuzimanje historije napretka u obliku excel dokumenta (za klijenta)
- ažuriranje rezultata klijenata (za trenera)
- ažuriranje uplata o mjesečnim članarinama (za trenera)
- chat platformu u realnom vremenu u kojem korisnici mogu komunicirati sa svojim trenerom i njegovim ostalim klijentima


# 🔧 Instalacija
1. Download-ajte projekat ili klonirajte repozitorij uz pomoć sljedećih komandi:
```
gh repo clone mkokor/fithub
```

Projekat je moguće pokrenuti na dva načina, preko Docker-a ili manuelno. S obzirom da je CPU opterećenje u slučaju pokretanja uz Docker izuzetno visoko (preko 250%), preporuka je da se aplikacija ne pokreće na taj način, nego ručno, što će biti opisano u nastavku.

Prvo pokrenite backened projekat prema sljedećim uputama:
1. Importujte backend projekat u razvojno okruženje
2. Na portu 3306 obezbijedite prazne baze naziva: fithub_auth, fithub_chat, fithub_membership, fithub_mealplan, fithub_training
3. Za svaki od podprojekata uradite niz akcija Maven clean -> Update Maven project -> Maven install -> pokrenite izvršni fajl kao Java aplikaciju (izvršni fajl nalazi se u src folderu u folderu NAZIV_MIKROSERVISE-service

      Podprojekte pokrenite sljedećim redoslijedom fithub-config-server -> eureka-service-registry -> system-events-service -> auth-service -> mealplan-service -> chat-service -> training-service -> membership-service ->api-gateway
4. Backend je sada pokrenut

Za pokretanje frontend projekta potrebno je izvršiti sljedeće korake
1. Pozicionirajte se u frontend folder (fithub-fe)
2. Preuzmite potrebne dependency-je uz pomoć sljedeće komande:
```
npm install
```
3. Pokrenite aplikaciju sljedećom komandom
```
npm start
```


