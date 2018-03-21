package com.music.controllers;

import com.music.TemplateFx.SongListModel;
import com.music.TemplateFx.SongsFx;
import com.music.TemplateFx.SongsModel;
import com.music.database.dao.SongsDao;
import com.music.database.models.Songs;
import com.music.utils.DialogsUtils;
import com.music.utils.exceptions.ApplicationException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.io.Console;
import java.util.List;

public class BoyerSearchController {

    @FXML
    private TextArea searchTextArea;

    @FXML
    private TextFlow searchFlow;

    @FXML
    private Button searchButton;

    private SongsModel songsModel;
    private SongsDao songsDao;
    private SongListModel songListModel;
    private SongsFx songsFx;

    public void initialize() {
        this.songListModel = new SongListModel();
        try {
            this.songListModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();

        songListModel.getSongsFxObservableList();
    }

    public void bindings(){
        this.songListModel.getSongsFxObservableList();
    }



    private final static int ASIZE = 5000;
    private static int bad_character_shift[] = new int[ASIZE];
    private static int good_suffix_shift[];
    private static int suff[];

    //prepare bad character shift table
    private static void pre_bad_character_shift(String pattern)
    {
        int m = pattern.length();

        for (int i = 0; i < ASIZE; i++)
        {
            bad_character_shift[i] = m;
        }

        for (int i = 0; i < m - 1; ++i)
        {
            bad_character_shift[pattern.charAt(i)] = m - i - 1;
        }
    }

    //prepare suff table
    private static void pre_suff(String pattern)
    {
        int j;
        int m = pattern.length();
        suff = new int[m];

        suff[m - 1] = m;
        for (int i = m - 2; i >= 0; --i) {
            for (j = 0; j <= i && pattern.charAt(i-j) == pattern.charAt(m-j-1); j++);
            suff[i] = j;
        }

    }

    //prepare good_suffix_shift table
    private static void pre_good_suffix_shift(String pattern)
    {
        int j = 0;
        int m = pattern.length();
        good_suffix_shift = new int[m];

        pre_suff(pattern);

        for (int i = 0; i < m; i++)
        {
            good_suffix_shift[i] = m;
        }

        j = 0;
        for (int i = m - 1; i >= 0; --i)
        {
            if (suff[i] == i + 1)
            {
                for (; j < m - 1 - i; ++j)
                {
                    good_suffix_shift[j] = m - 1 - i;
                }
            }
        }

        for (int i = 0; i <= m - 2; ++i)
        {
            good_suffix_shift[m - 1 - suff[i]] = m - 1 - i;
        }
    }

    //Boyer-Moore algorithm
    private static void BM_alg(String text, String pattern)
    {
        int i, j;
        int m = pattern.length();
        int n = text.length();

        pre_bad_character_shift(pattern);
        pre_good_suffix_shift(pattern);

        j = 0;
        while (j <= n - m) {
            for (i = m - 1; i >= 0 && pattern.charAt(i) == text.charAt(i + j); --i);
            if (i < 0) {
                System.out.print(j + " ");
                j += good_suffix_shift[0];
            }
            else
                j += Math.max(good_suffix_shift[i], bad_character_shift[text.charAt(i + j)] - m + 1 + i);
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {

        String text;
        String pattern;


////        System.out.println("Cokolwiek napisze bendzie tu XDDD" + this.songListModel.getSongsFxObservableList());
//
//        text = searchTextArea.getText();
//        text = Console.readString();
//
////        pattern = this.songListModel.getSongsFxObservableList().toString();
//
//        pattern = "Przez twe oczy, te oczy zielone oszalalem\n" +
//                "Gwiazdy Chyba twym oczom oddaly caly blask\n" +
//                "A ja serce milosci spragnione ci oddalem\n" +
//                "Tak zakochac, zakochac sie mozna tylko raz";

        System.out.println("Podaj tekst");
        text = Console.readString();

        System.out.println("Podaj wzorzec");
        pattern = Console.readString();

        BM_alg(text, pattern);


    }
}
