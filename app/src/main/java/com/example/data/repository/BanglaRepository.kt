package com.example.data.repository

import com.example.data.db.BanglaDao
import com.example.data.db.QuizScoreEntity
import com.example.data.db.SavedWordEntity
import com.example.data.model.AlphabetItem
import com.example.data.model.BlessingQuote
import com.example.data.model.PhraseItem
import com.example.data.model.QuizQuestion
import com.example.data.model.WordItem
import kotlinx.coroutines.flow.Flow

class BanglaRepository(private val dao: BanglaDao) {

    val savedWords: Flow<List<SavedWordEntity>> = dao.getAllSavedWords()
    val quizScores: Flow<List<QuizScoreEntity>> = dao.getAllQuizScores()
    val learnedCount: Flow<Int> = dao.getLearnedCount()
    val favoriteCount: Flow<Int> = dao.getFavoriteCount()

    suspend fun toggleFavorite(wordId: String, currentFav: Boolean) {
        val existing = dao.getSavedWordById(wordId)
        val updated = existing?.copy(
            isFavorite = !currentFav,
            updatedAt = System.currentTimeMillis()
        ) ?: SavedWordEntity(
            wordId = wordId,
            isFavorite = true,
            isLearned = false
        )
        dao.upsertSavedWord(updated)
    }

    suspend fun toggleLearned(wordId: String, currentLearned: Boolean) {
        val existing = dao.getSavedWordById(wordId)
        val updated = existing?.copy(
            isLearned = !currentLearned,
            updatedAt = System.currentTimeMillis()
        ) ?: SavedWordEntity(
            wordId = wordId,
            isFavorite = false,
            isLearned = true
        )
        dao.upsertSavedWord(updated)
    }

    suspend fun saveQuizScore(score: Int, total: Int) {
        val percentage = if (total > 0) (score * 100) / total else 0
        dao.insertQuizScore(
            QuizScoreEntity(
                score = score,
                totalQuestions = total,
                percentage = percentage
            )
        )
    }

    fun getAllWords(): List<WordItem> = listOf(
        WordItem(
            id = "w1",
            bangla = "নমস্কার",
            english = "Hello / Greetings",
            pronunciation = "nomoshkar",
            category = "Greetings",
            exampleBangla = "নমস্কার, আপনি কেমন আছেন?",
            exampleEnglish = "Hello, how are you?",
            funFact = "The traditional respectful greeting with folded hands."
        ),
        WordItem(
            id = "w2",
            bangla = "ধন্যবাদ",
            english = "Thank you",
            pronunciation = "dhonnobad",
            category = "Greetings",
            exampleBangla = "আপনার সাহায্যের জন্য ধন্যবাদ।",
            exampleEnglish = "Thank you for your help.",
            funFact = "Derived from Sanskrit 'Dhanya' meaning blessed/gratitude."
        ),
        WordItem(
            id = "w3",
            bangla = "আমি",
            english = "I / Me",
            pronunciation = "ami",
            category = "Essentials",
            exampleBangla = "আমি বাংলা শিখছি।",
            exampleEnglish = "I am learning Bangla.",
            funFact = "The most frequent pronoun in conversational Bengali."
        ),
        WordItem(
            id = "w4",
            bangla = "ভালোবাসি",
            english = "Love",
            pronunciation = "bhalobashi",
            category = "Emotion",
            exampleBangla = "আমি তোমাকে ভালোবাসি।",
            exampleEnglish = "I love you.",
            funFact = "One of the most poetic expressions in Bengali literature."
        ),
        WordItem(
            id = "w5",
            bangla = "পানি",
            english = "Water",
            pronunciation = "pani",
            category = "Nature & Food",
            exampleBangla = "আমি একটু পানি খাব।",
            exampleEnglish = "I want to drink some water.",
            funFact = "In West Bengal 'জল' (jol) is also widely used."
        ),
        WordItem(
            id = "w6",
            bangla = "বন্ধু",
            english = "Friend",
            pronunciation = "bondhu",
            category = "People",
            exampleBangla = "তুমি আমার সবচেয়ে ভালো বন্ধু।",
            exampleEnglish = "You are my best friend.",
            funFact = "Celebrated in classic Rabindra Sangeet."
        ),
        WordItem(
            id = "w7",
            bangla = "শুভ সকাল",
            english = "Good Morning",
            pronunciation = "shubho shokal",
            category = "Greetings",
            exampleBangla = "শুভ সকাল! দিনটি ভালো কাটুক।",
            exampleEnglish = "Good morning! Have a wonderful day.",
            funFact = "'Shubho' signifies divine auspiciousness."
        ),
        WordItem(
            id = "w8",
            bangla = "কেমন আছেন?",
            english = "How are you? (polite)",
            pronunciation = "kemon achen?",
            category = "Greetings",
            exampleBangla = "নমস্কার প্রশান্ত বাবু, কেমন আছেন?",
            exampleEnglish = "Hello Prashant ji, how are you?",
            funFact = "Used to show reverence and respect."
        ),
        WordItem(
            id = "w9",
            bangla = "ভালো",
            english = "Good / Well",
            pronunciation = "bhalo",
            category = "Essentials",
            exampleBangla = "আমি খুব ভালো আছি।",
            exampleEnglish = "I am doing very well.",
            funFact = "Can be used as an adjective or adverb."
        ),
        WordItem(
            id = "w10",
            bangla = "শান্তি",
            english = "Peace",
            pronunciation = "shanti",
            category = "Emotion",
            exampleBangla = "ঈশ্বরের আশীর্বাদে অন্তরে শান্তি থাকে।",
            exampleEnglish = "With God's blessings, inner peace abides.",
            funFact = "Lord Shiva brings profound transcendent peace."
        ),
        WordItem(
            id = "w11",
            bangla = "চা",
            english = "Tea",
            pronunciation = "cha",
            category = "Nature & Food",
            exampleBangla = "চলুন এক কাপ চা খাই।",
            exampleEnglish = "Let's drink a cup of tea.",
            funFact = "Adda (conversations) over Cha is legendary in Bengal."
        ),
        WordItem(
            id = "w12",
            bangla = "ভাত",
            english = "Rice",
            pronunciation = "bhat",
            category = "Nature & Food",
            exampleBangla = "বাঙালিরা ভাত ভালোবাসে।",
            exampleEnglish = "Bengalis love rice.",
            funFact = "The staple food of Bengal."
        ),
        WordItem(
            id = "w13",
            bangla = "সূর্য",
            english = "Sun",
            pronunciation = "shurjo",
            category = "Nature & Food",
            exampleBangla = "পূর্ব দিকে সূর্য উঠছে।",
            exampleEnglish = "The sun is rising in the east.",
            funFact = "Bringer of cosmic radiance."
        ),
        WordItem(
            id = "w14",
            bangla = "চাঁদ",
            english = "Moon",
            pronunciation = "chãd",
            category = "Nature & Food",
            exampleBangla = "শিবের মাথায় সুন্দর চাঁদ থাকে।",
            exampleEnglish = "The beautiful crescent moon adorns Shiva's head.",
            funFact = "Lord Shiva is Chandrashekhara (wearer of the moon)."
        ),
        WordItem(
            id = "w15",
            bangla = "মা",
            english = "Mother",
            pronunciation = "ma",
            category = "People",
            exampleBangla = "মা আমার অনুপ্রেরণা।",
            exampleEnglish = "Mother is my inspiration.",
            funFact = "The sacred divine feminine maternal spirit."
        ),
        WordItem(
            id = "w16",
            bangla = "বাবা",
            english = "Father",
            pronunciation = "baba",
            category = "People",
            exampleBangla = "বাবা আমাকে পথ দেখান।",
            exampleEnglish = "Father guides my path.",
            funFact = "Also an affectionate title for Lord Shiva ('Bhole Baba')."
        ),
        WordItem(
            id = "w17",
            bangla = "হ্যাঁ",
            english = "Yes",
            pronunciation = "hã",
            category = "Essentials",
            exampleBangla = "হ্যাঁ, আমি নিশ্চিত।",
            exampleEnglish = "Yes, I am certain.",
            funFact = "Spoken with a gentle nasal tone."
        ),
        WordItem(
            id = "w18",
            bangla = "না",
            english = "No",
            pronunciation = "na",
            category = "Essentials",
            exampleBangla = "না, কোনো সমস্যা নেই।",
            exampleEnglish = "No, there is no problem.",
            funFact = "Simple and universal."
        ),
        WordItem(
            id = "w19",
            bangla = "দয়া করে",
            english = "Please",
            pronunciation = "doya kore",
            category = "Greetings",
            exampleBangla = "দয়া করে এখানে বসুন।",
            exampleEnglish = "Please sit here.",
            funFact = "Literally means 'acting with kindness/compassion'."
        ),
        WordItem(
            id = "w20",
            bangla = "বিদায়",
            english = "Goodbye",
            pronunciation = "biday",
            category = "Greetings",
            exampleBangla = "আজ বিদায়, আবার দেখা হবে।",
            exampleEnglish = "Goodbye for today, see you again.",
            funFact = "Often replaced colloquially with 'Abar dekha hobe' (See you again)."
        ),
        WordItem(
            id = "w21",
            bangla = "এক",
            english = "One (1)",
            pronunciation = "ek",
            category = "Numbers",
            exampleBangla = "আমাদের সবার ঈশ্বর এক।",
            exampleEnglish = "God is one for all of us.",
            funFact = "Bengali digit is ১."
        ),
        WordItem(
            id = "w22",
            bangla = "দুই",
            english = "Two (2)",
            pronunciation = "dui",
            category = "Numbers",
            exampleBangla = "আমার কাছে দুইটা বই আছে।",
            exampleEnglish = "I have two books.",
            funFact = "Bengali digit is ২."
        ),
        WordItem(
            id = "w23",
            bangla = "তিন",
            english = "Three (3)",
            pronunciation = "tin",
            category = "Numbers",
            exampleBangla = "শিবের ত্রিশূলের তিনটি ধার আছে।",
            exampleEnglish = "Shiva's Trishul has three prongs.",
            funFact = "Bengali digit is ৩."
        ),
        WordItem(
            id = "w24",
            bangla = "সুন্দর",
            english = "Beautiful",
            pronunciation = "shundor",
            category = "Emotion",
            exampleBangla = "বাংলা ভাষা খুব সুন্দর।",
            exampleEnglish = "The Bengali language is very beautiful.",
            funFact = "Part of 'Satyam Shivam Sundaram'."
        )
    )

    fun getAlphabetList(): List<AlphabetItem> = listOf(
        // Vowels (স্বরবর্ণ)
        AlphabetItem("অ", "o / aw", "Inherent 'aw' sound as in 'hot'", "অনেক (onek)", "Many / A lot", true),
        AlphabetItem("আ", "a / aa", "Open 'aa' sound as in 'father'", "আম (aam)", "Mango", true),
        AlphabetItem("ই", "i / ee", "Short 'i' as in 'pin'", "ইলিশ (ilish)", "Hilsa fish", true),
        AlphabetItem("ঈ", "ee / long i", "Long 'ee' as in 'feel'", "ঈশ্বর (ishwor)", "God / Divine", true),
        AlphabetItem("উ", "u / oo", "Short 'oo' as in 'put'", "উট (ut)", "Camel", true),
        AlphabetItem("ঊ", "oo / long u", "Long 'oo' as in 'moon'", "ঊষা (usha)", "Dawn", true),
        AlphabetItem("ঋ", "ri / rree", "Vocalic 'ri' sound", "ঋষি (rishi)", "Sage / Hermit", true),
        AlphabetItem("এ", "e / ay", "Sound as in 'bed' or 'say'", "এক (ek)", "One", true),
        AlphabetItem("ঐ", "oi / oy", "Diphthong as in 'boy'", "ঐক্য (oikyo)", "Unity", true),
        AlphabetItem("ও", "o / oh", "Pure 'o' as in 'boat'", "ওজন (ojon)", "Weight", true),
        AlphabetItem("ঔ", "ou / ow", "Diphthong as in 'how'", "ঔষধ (oushodh)", "Medicine", true),

        // Consonants (ব্যঞ্জনবর্ণ)
        AlphabetItem("ক", "ka", "Velar voiceless 'k' as in 'kite'", "কলম (kolom)", "Pen", false),
        AlphabetItem("খ", "kha", "Aspirated 'kh' as in 'block-head'", "খাতা (khata)", "Notebook", false),
        AlphabetItem("গ", "ga", "Voiced 'g' as in 'go'", "গান (gaan)", "Song", false),
        AlphabetItem("ঘ", "gha", "Aspirated 'gh' sound", "ঘর (ghor)", "House / Room", false),
        AlphabetItem("ঙ", "nga", "Nasal velar sound as in 'sing'", "রঙ (rong)", "Color", false),
        AlphabetItem("চ", "cha", "Palatal 'ch' as in 'chair'", "চা (cha)", "Tea", false),
        AlphabetItem("ছ", "chha", "Aspirated 'chh' sound", "ছবি (chobi)", "Picture", false),
        AlphabetItem("জ", "ja", "Voiced 'j' as in 'joy'", "জল (jol)", "Water", false),
        AlphabetItem("ঝ", "jha", "Aspirated 'jh' sound", "ঝড় (jhor)", "Storm", false),
        AlphabetItem("ট", "ta (retroflex)", "Hard retroflex 't'", "টাকা (taka)", "Money", false),
        AlphabetItem("ঠ", "tha (retroflex)", "Aspirated retroflex 'th'", "ঠিক (thik)", "Right / Correct", false),
        AlphabetItem("ড", "da (retroflex)", "Hard retroflex 'd'", "ডালিম (dalim)", "Pomegranate", false),
        AlphabetItem("ত", "to (dental)", "Soft dental 't' as in Italian", "তারা (tara)", "Star", false),
        AlphabetItem("থ", "tho (dental)", "Soft aspirated dental 'th'", "থালা (thala)", "Plate", false),
        AlphabetItem("দ", "do (dental)", "Soft dental 'd'", "দুধ (dudh)", "Milk", false),
        AlphabetItem("ধ", "dho (dental)", "Aspirated dental 'dh'", "ধন্যবাদ (dhonnobad)", "Thank you", false),
        AlphabetItem("ন", "no", "Dental nasal 'n'", "নমস্কার (nomoshkar)", "Greetings", false),
        AlphabetItem("প", "po", "Voiceless bilabial 'p'", "পানি (pani)", "Water", false),
        AlphabetItem("ফ", "pho / fo", "Aspirated 'ph'", "ফুল (ful)", "Flower", false),
        AlphabetItem("ব", "bo", "Voiced bilabial 'b'", "বন্ধু (bondhu)", "Friend", false),
        AlphabetItem("ভ", "bho", "Aspirated 'bh'", "ভালোবাসা (bhalobasha)", "Love", false),
        AlphabetItem("ম", "mo", "Nasal 'm'", "মা (ma)", "Mother", false),
        AlphabetItem("র", "ro", "Alveolar tap 'r'", "রাস্তা (rasta)", "Road", false),
        AlphabetItem("ল", "lo", "Lateral 'l'", "লাল (laal)", "Red", false),
        AlphabetItem("শ", "sho", "Palatal 'sh'", "শান্তি (shanti)", "Peace", false),
        AlphabetItem("হ", "ho", "Glottal 'h'", "হাসি (hashi)", "Smile", false)
    )

    fun getQuizQuestions(): List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "What does 'নমস্কার' (nomoshkar) mean?",
            options = listOf("Goodbye", "Hello / Greetings", "Thank you", "Please"),
            correctIndex = 1,
            explanation = "'নমস্কার' (nomoshkar) is the reverent traditional greeting in Bangla.",
            targetWordBangla = "নমস্কার"
        ),
        QuizQuestion(
            id = 2,
            question = "Which word means 'Water' in Bangla?",
            options = listOf("বন্ধু (bondhu)", "ভালোবাসি (bhalobashi)", "পানি (pani)", "আমি (ami)"),
            correctIndex = 2,
            explanation = "'পানি' (pani) and 'জল' (jol) both mean water in Bangla.",
            targetWordBangla = "পানি"
        ),
        QuizQuestion(
            id = 3,
            question = "How do you say 'Thank you' in Bangla?",
            options = listOf("ধন্যবাদ (dhonnobad)", "নমস্কার (nomoshkar)", "আমি (ami)", "পানি (pani)"),
            correctIndex = 0,
            explanation = "'ধন্যবাদ' (dhonnobad) expresses gratitude and thanks.",
            targetWordBangla = "ধন্যবাদ"
        ),
        QuizQuestion(
            id = 4,
            question = "What is the Bangla word for 'Friend'?",
            options = listOf("ভালোবাসি (bhalobashi)", "বন্ধু (bondhu)", "আমি (ami)", "ধন্যবাদ (dhonnobad)"),
            correctIndex = 1,
            explanation = "'বন্ধু' (bondhu) is the warm word for friend in Bangla.",
            targetWordBangla = "বন্ধু"
        ),
        QuizQuestion(
            id = 5,
            question = "What does 'ভালোবাসি' (bhalobashi) mean?",
            options = listOf("To eat", "To sleep", "Love / I love", "To walk"),
            correctIndex = 2,
            explanation = "'ভালোবাসি' (bhalobashi) comes from bhalobasha (love).",
            targetWordBangla = "ভালোবাসি"
        ),
        QuizQuestion(
            id = 6,
            question = "How do you say 'I' or 'Me' in Bangla?",
            options = listOf("তুমি (tumi)", "আমি (ami)", "সে (she)", "আমরা (amra)"),
            correctIndex = 1,
            explanation = "'আমি' (ami) means 'I'. 'তুমি' is you, 'আমরা' is we.",
            targetWordBangla = "আমি"
        ),
        QuizQuestion(
            id = 7,
            question = "What does 'শান্তি' (shanti) translate to?",
            options = listOf("Storm", "Peace", "Fire", "Knowledge"),
            correctIndex = 1,
            explanation = "'শান্তি' (shanti) means inner tranquility and peace.",
            targetWordBangla = "শান্তি"
        ),
        QuizQuestion(
            id = 8,
            question = "Which word represents 'Sun' in Bangla?",
            options = listOf("চাঁদ (chãd)", "নদী (nodi)", "সূর্য (shurjo)", "ফুল (ful)"),
            correctIndex = 2,
            explanation = "'সূর্য' (shurjo) is the sun. 'চাঁদ' is the moon.",
            targetWordBangla = "সূর্য"
        )
    )

    fun getBlessingQuotes(): List<BlessingQuote> = listOf(
        BlessingQuote(
            id = 1,
            sacredTitle = "ॐ नमः शिवाय · Shiva Divine Grace",
            mantraOrVerse = "জ্ঞানেই শিব, বাক্যেই সৌন্দর্য",
            meaning = "In pure knowledge resides Lord Shiva; in truthful speech dwells divine beauty.",
            blessingNote = "Prashant Upadhyaya wishes you success, clarity, and peace on your learning journey."
        ),
        BlessingQuote(
            id = 2,
            sacredTitle = "সত্যম শিবম সুন্দরম · Divine Harmony",
            mantraOrVerse = "সহজ বাংলায় মনের কথা প্রকাশ হোক",
            meaning = "May your heartfelt thoughts flow effortlessly through the melodious sweetness of Bangla.",
            blessingNote = "Every word you learn bridges cultures, hearts, and souls."
        ),
        BlessingQuote(
            id = 3,
            sacredTitle = "ত্রিশূল ও ডমরু · Awakening Knowledge",
            mantraOrVerse = "নাদব্রহ্মে সুর বাজে, ভাষায় খোলে দ্বার",
            meaning = "As Lord Shiva's Damru awakens cosmic rhythm, language opens the door to infinite wisdom.",
            blessingNote = "Practice daily with faith and joyful curiosity."
        )
    )

    fun getPhrases(): List<PhraseItem> = listOf(
        PhraseItem("p1", "নমস্কার, আপনি কেমন আছেন?", "Nomoshkar, apni kemon achen?", "Hello, how are you?", "Greetings"),
        PhraseItem("p2", "আমি ভালো আছি, ধন্যবাদ।", "Ami bhalo achi, dhonnobad.", "I am fine, thank you.", "Greetings"),
        PhraseItem("p3", "আপনার নাম কি?", "Apnar naam ki?", "What is your name?", "Introductions"),
        PhraseItem("p4", "আমার নাম প্রশান্ত।", "Amar naam Prashant.", "My name is Prashant.", "Introductions"),
        PhraseItem("p5", "ঈশ্বর আপনার মঙ্গল করুন।", "Ishwor apnar mongol korun.", "May God bless you with auspiciousness.", "Divine Blessings"),
        PhraseItem("p6", "আবার দেখা হবে!", "Abar dekha hobe!", "See you again!", "Parting"),
        PhraseItem("p7", "এক কাপ চা দেবেন?", "Ek cup cha deben?", "Could you please give me a cup of tea?", "Dining"),
        PhraseItem("p8", "বাংলা ভাষা খুব মিষ্টি।", "Bangla bhasha khub mishti.", "The Bengali language is very sweet.", "Praise")
    )
}
