Tietorakenteiden harjoitustyö

A*-haku

Tietorakenteiden harjoitustyö, kesä 2010, ryhmä 2

Joni Mäkelä 
joni.makela@gmail.com 
Tietojenkäsittelytieteen laitos 
Helsingin yliopisto

1. Aiheen määrittely

Aiheena on A*-hakualgoritmi, jota käytetään yleisesti peleissä etsimään ratkaisuja erilaisista graafeista ja/tai puista, jotka kuvastavat esimerkiksi jonkun maaston mahdollisia kuljettavia reittejä sekä reittien välisiä pisteitä. Algoritmi arvioi pisteitä funktiolla f(n)=g(n)+h(n), jossa g(n) kuvastaa pisteestä viereiseen pisteeseen liikkumisen kustannusta, ja h(n) on kustannusarvio nykyisestä solmusta maalitilaan. Algoritmi on optimaalinen jos h ei koskaan yliarvioi kustannusta maalisolmun saavuttamiseen.
Algoritmeina käytetään luonnollisesti itse A*-algoritmia, sekä aputietorakenteena minimikekoa.
Syötteenä ohjelma lukee .bmp kuvatiedoston, jonka täytyy olla harmaakuva. Täysin valkoinen piste tulkitaan kuljettavaksi, muut värit tulkitaan ei-kuljettaviksi. Kuvasta ohjelma luo kaksiulotteisen taulukon jossa itse navigointi tapahtuu. Liikkuminen on mahdollista kaikkiin kahdeksaan ilmansuuntaan, horisontaalisesti, vertikaalisesti sekä diagonaalisesti.
Halutut aika- ja tilavaativuudet ovat molemmat O(n), kuvapisteiden määrä sekä tutkittavien pisteiden määrä.

2. Käyttöohje

Ohjelma on valmiiksi käännetty ajettavaksi .jar tiedostoksi, ajaminen Windowsin puolella onnistuu tuplaklikkaamalla .jar tiedostoa. Linuxin puolella ajaminen tapahtuu komentorivin kautta komennolla "java -jar Tiralabra.jar".
Ohjelmaa käytetään klikkaamalla kuvaa kaksi kertaa. Aluksi valitaan polun alkupiste, jonka jälkeen valitaan loppupiste. Tämän jälkeen algoritmi etsii optimaalisimman reitin näiden pisteiden välille.
Ohjelma lukee kuvatiedoston jota käytetään reitinhaun simuloimisen "maastona", kuvatiedoston pitää olla samassa kansiossa itse .jar tiedoston kanssa.

3. Toteutuksen kuvaus

ohjelman yleisrakenne
perustelut valittujen ratkaisujen käyttöön
aika- ja tilavaativuudet

Ohjelmassa on viisi luokkaa. Main, ImagePanel, MinHeap, ja Node.
Main on ohjelman pääluokka, algoritmin kannalta tässä luokassa ei ole mitään oleellista.
ImagePanel on koko homman pihvi, sieltä löytyy reitin laskentaan käytettävät algoritmit.
MinHeap on aputietorakenne jota käytetään astar() metodin apuna.
Node on puolestaan kartan jokaista pikseliä kuvaava luokka. Tästä löytyy G,H ja F arvot, sekä RGB arvo että pisteen koordinaatit ja lopuksi vielä pisteen parent piste. 
A*-algoritmi toimii seuraavalla tavalla:

	1. Pistetään alkupiste minimikekoon

	Niin kauan kun keossa vielä on tavaraa...

	2. Otetaan minimikeosta käsittelyyn piste "current", samalla se poistuu keosta.
	3. Tämän jälkeen current pistetään suljettuun listaan, jonka virkaa toimittaa kaksiulotteinen taulukko.
	4. Jos tämän hetkinen piste ei ole maali, tutkitaan sen jokainen naapuri.
	5. Jokaiselle naapurille lasketaan siihen liikkumiseen kustannuksen nykyisestä pisteestä sekä sen etäisyysarvio maalista.
	6. Jos naapuri on jo suljetussa listassa, skipataan se.
	7. Muutoin asetetaan naapurin parentiksi current piste.
	8. Lisätään naapuri minimikekoon.
	9. Lisätään naapuri suljettuun listaan.

Aikavaativuus on O(n), tilavaativuus on myös O(n) (n on kuvassa olevien pikselien määrä)

4. Toteutuksen puutteet

Algoritmi voi syödä aivan älyttömästi muistia, riippuen toki itse tutkittavan kuvan alueesta. Yksi ratkaisu olisi tehdä iteratiivinen versio algoritmista, jossa etsittäisiin aina esim. kymmenen pisteen pituisia matkoja. Tämän avulla minimikeko pysyisi kurissa.

Lähteet:

http://en.wikipedia.org/wiki/A*_search_algorithm
http://www.policyalmanac.org/games/aStarTutorial.htm
