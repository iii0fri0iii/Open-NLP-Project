# Open-NLP-Project
идеи:
- убрать пунктуацию
- однокоренные слова, POS tags - статистика 
- ограничить количество результатов
- зависимость POS tag'a места в предложении
- предсказание: после такого-то слова встречается такой-то POS tag в таких-то случаях

заметки:
- детектинг чисел


что написать в репорте:

1. мы хотим все сделать в gui interface
2. будут обязательные фичи: поиск по лемме, POS и слову; количество соседей слева и справа и что-то там ещё обязательное
3. во-первых, мы хотим добавить possibility to choose the amount of displayed results
4. во-вторых, мы хотим добавить возможность сортировать выданные результаты по части речи (когда user делает поиск по лемме/слову, в окошке sort_by_pos появляются все возможные варианты pos этого слова, и user может выбрать, какой именно pos ему нужен)

5. уже сделано: nlp методы
https://us05web.zoom.us/j/81943613963?pwd=L1daTjRZZUZweGcwYkRWaUF1QTRBQT09

CorpusBuilder corp= new CorpusBuilder(inputStream);
                corp.getSentences();
                ArrayList<String> src=new ArrayList<String>();
                
                for(int i=0; i<corp.getSentences().length; i++){
                    src.add(corp.getWordPosLemma());
                }
                
                
                inputStream.close();


            }
        }
