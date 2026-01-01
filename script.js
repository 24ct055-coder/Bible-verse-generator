let current = {};

async function newVerse() {
    const res = await fetch("https://bible-api.com/?random=verse&translation=web");
    const data = await res.json();

    current.book = data.verses[0].book_name.toLowerCase();
    current.chapter = data.verses[0].chapter;
    current.verse = data.verses[0].verse;

    document.getElementById("reference").innerText = data.reference;
    document.getElementById("text").innerText = `"${data.text.trim()}"`;
}

async function showVerse() {
    await newVerse();
}

async function showParagraph() {
    const start = Math.max(1, current.verse - 2);
    const end = current.verse + 2;

    const res = await fetch(
        `https://bible-api.com/${current.book}%20${current.chapter}:${start}-${end}?translation=web`
    );
    const data = await res.json();

    document.getElementById("reference").innerText =
        `${current.book.toUpperCase()} ${current.chapter}:${start}-${end}`;

    document.getElementById("text").innerText =
        data.verses.map(v => v.text.trim()).join("\n\n");
}

async function showChapter() {
    const res = await fetch(
        `https://bible-api.com/${current.book}%20${current.chapter}?translation=web`
    );
    const data = await res.json();

    document.getElementById("reference").innerText =
        `${current.book.toUpperCase()} ${current.chapter}`;

    document.getElementById("text").innerText =
        data.verses.map(v => `${v.verse}. ${v.text.trim()}`).join("\n\n");
}

newVerse();
