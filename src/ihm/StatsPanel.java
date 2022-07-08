package ihm;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import databases.DataBase;
import databases.ResultArray;

public class StatsPanel {

    public static JCustomTabbedPane tabPanel;

    private static int projectID;
    private static DataBase db = DataBase.db;

    private static HashMap<String, Color> missionColors;
    private static ResultArray project;

    private static String[] monthNames = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août",
            "Septembre", "Octobre", "Novembre", "Décembre" };

    public static JPanel getPanel(MainFrame frame, int projectID, int persID) {
        missionColors = new HashMap<>();
        StatsPanel.projectID = projectID;

        project = db.getProject(projectID);

        String nomProjet = (String) project.get(0, "nom_projet");
        String clientProjet = (String) project.get(0, "client_projet");

        JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        innerPanel.setBackground(new Color(251, 251, 251));

        JRoundedButton goBackBtn = new JRoundedButton();
        ImageIcon goBackIconD = new ImageIcon(
                new ImageIcon("img/icone_back.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon goBackIconH = new ImageIcon(
                new ImageIcon("img/icone_back_hover.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        goBackBtn.setMultipleIcon(goBackIconD, goBackIconH, null);
        goBackBtn.setToolTipText("Retour à mes projets");
        goBackBtn.setPreferredSize(new Dimension(34, 34));
        goBackBtn.setName("statsPanel/goBack");
        goBackBtn.addActionListener(frame);

        JRoundedButton printBtn = new JRoundedButton();
        ImageIcon printBtnD = new ImageIcon(
                new ImageIcon("img/icone_print.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon printBtnH = new ImageIcon(
                new ImageIcon("img/icone_print_hover.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        printBtn.setMultipleIcon(printBtnD, printBtnH, null);
        printBtn.setToolTipText("Imprimer");
        printBtn.setPreferredSize(new Dimension(34, 34));
        printBtn.setName("statsPanel/print");
        printBtn.addActionListener(frame);

        String titleStyle = "style='font-weight:normal; font-size:14px'";
        String titleS = String.format("<html><span %s>DÉTAILS DU PROJET</span></html>", titleStyle);

        String nomProjetStyle = "style='font-weight:bold; font-size:11px'";
        String clientProjetStyle = "style='font-weight:bold; font-size:10px; font-style:italic'";

        String titleProjectS = String.format(
                "<html><span style='display:inline-block'><span %s> %s </span><span %s> %s </span></span></html>",
                nomProjetStyle, nomProjet.toUpperCase(), clientProjetStyle, clientProjet);

        JLabel titleLabel = new JLabel(titleS, JLabel.CENTER);
        JLabel titleProjectLabel = new JLabel(titleProjectS, JLabel.CENTER);

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentSecond = calendar.get(Calendar.SECOND);
        String currentDateStyle = "style='font-weight:normal; font-style:italic; font-size:9px; font-family:Optima'";
        String currentDateS = String.format("<html><span %s>Version du %02d/%02d/%d à %02d:%02d:%02d", currentDateStyle,
                currentDay, currentMonth, currentYear, currentHour, currentMinute, currentSecond);

        JLabel currentDateLabel = new JLabel(currentDateS, JLabel.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        innerPanel.add(goBackBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        innerPanel.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);
        innerPanel.add(titleProjectLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);
        innerPanel.add(currentDateLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        innerPanel.add(printBtn, gbc);

        tabPanel = new JCustomTabbedPane();

        ResultArray participants = db.getParticipants(projectID);

        JPanel p1 = getStatsByMissionPanel(participants);
        JPanel p2 = getStatsByMonthPanel(participants);

        p1.setBackground(new Color(251, 251, 251));
        p2.setBackground(new Color(251, 251, 251));

        JPanel p1scroll = new JPanel(new BorderLayout());
        JPanel p2scroll = new JPanel(new BorderLayout());

        JScrollPane p1jsp = (new JScrollPane(p1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        p1jsp.getVerticalScrollBar().setUnitIncrement(4);
        p1scroll.add(p1jsp);

        JScrollPane p2jsp = (new JScrollPane(p2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        p2jsp.getVerticalScrollBar().setUnitIncrement(4);
        p2scroll.add(p2jsp);

        tabPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        tabPanel.add(
                "<html><span style='font-size:11px; font-weight:normal; font-family:Optima'>Statistiques par mission</span></html>",
                p1scroll);
        tabPanel.add(
                "<html><span style='font-size:11px; font-weight:normal; font-family:Optima'>Statistiques par mois</span></html>",
                p2scroll);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        innerPanel.add(tabPanel, gbc);
        ;

        p1scroll.setPreferredSize(new Dimension(1, 1));
        p2scroll.setPreferredSize(new Dimension(1, 1));

        printBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Print p;
                if (tabPanel.getSelectedIndex() == 0) {
                    p = new Print(p1);
                } else if (tabPanel.getSelectedIndex() == 1) {
                    p = new Print(p2);
                } else {
                    p = new Print(innerPanel);
                }
                p.print();

            }
        });

        return JPanelOperations.setBorders(innerPanel, 5.0);
    }

    private static JPanel getStatsByMissionPanel(ResultArray participants) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        Border cellLineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border cellEmptyBorder = new EmptyBorder(7, 12, 7, 12);
        CompoundBorder cellBorder = new CompoundBorder(cellLineBorder, cellEmptyBorder);

        for (int j = 0; j < participants.size; j++) {

            String nom = (String) participants.get(j, "nom");
            String prenom = (String) participants.get(j, "prenom");
            int participantID = (int) participants.get(j, "id");

            String headerStyle = "style='font-weight:bold; font-size:14px; font-family:Optima; color:#444444'";
            String headerS = String.format("<html><span %s> %s %s </span></html>", headerStyle, nom.toUpperCase(),
                    prenom);

            JLabel headerLabel = new JLabel(headerS, JLabel.CENTER);

            headerLabel.setBorder(cellBorder);

            gbc.gridx = j;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(headerLabel, gbc);

            ResultArray myStatsRes = db.getMyStatsGroupByMission(projectID, participantID);

            JPanel columnPanel = StatsPanel.getStatsByMissionColumn(myStatsRes);

            gbc.gridx = j;
            gbc.gridy = 1;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(columnPanel, gbc);

            int myTotalWorkTime = 0;
            for (int k = 0; k < myStatsRes.size; k++) {
                myTotalWorkTime += (int) myStatsRes.get(k, "temps_travail_total");
            }

            String bottomStyle = "style='font-weight:bold; font-size:13px; font-family:Optima; color:#444444'";
            String bottomS = String.format("<html><span %s> %d heures </span></html>", bottomStyle,
                    myTotalWorkTime / 3600);

            JLabel bottomLabel = new JLabel(bottomS, JLabel.CENTER);

            bottomLabel.setBorder(cellBorder);

            gbc.gridx = j;
            gbc.gridy = 2;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(bottomLabel, gbc);
        }

        String headerStyle = "style='font-weight:bold; font-size:14px'; font-family:Optima; color:#444444";
        String headerS = String.format("<html><span %s> TOTAL </span></html>", headerStyle);

        JLabel headerLabel = new JLabel(headerS, JLabel.RIGHT);

        headerLabel.setBorder(cellBorder);

        gbc.gridx = participants.size;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(headerLabel, gbc);

        ResultArray totalStatsRes = db.getTotalStatsGroupByMission(projectID);

        JPanel columnPanel = StatsPanel.getStatsByMissionColumn(totalStatsRes);

        gbc.gridx = participants.size;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(columnPanel, gbc);

        int totalWorkTime = 0;
        for (int k = 0; k < totalStatsRes.size; k++) {
            totalWorkTime += (int) totalStatsRes.get(k, "temps_travail_total");
        }

        String bottomStyle = "style='font-weight:bold; font-size:13px; font-family:Optima; color:#44444'";
        String bottomS = String.format("<html><span %s> %d heures </span></html>", bottomStyle, totalWorkTime / 3600);

        JLabel bottomLabel = new JLabel(bottomS, JLabel.CENTER);

        bottomLabel.setBorder(cellBorder);

        gbc.gridx = participants.size;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(bottomLabel, gbc);

        gbc.gridx = participants.size;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(columnPanel, gbc);

        return panel;
    }

    public static JPanel getStatsByMissionColumn(ResultArray statRes) {
        JPanel columnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcColumn = new GridBagConstraints();

        columnPanel.setBackground(new Color(245, 245, 245));
        columnPanel.setOpaque(true);

        int myTotalWorkTime = 0;
        for (int k = 0; k < statRes.size; k++) {
            myTotalWorkTime += (int) statRes.get(k, "temps_travail_total");
        }

        for (int i = 0; i < statRes.size; i++) {
            String statMission = (String) statRes.get(i, "mission");
            int statMissionWorkTime = (int) statRes.get(i, "temps_travail_total");
            long statMissionDebutTimestamp = ((Number) statRes.get(i, "date_debut")).longValue();
            long statMissionFinTimestamp = ((Number) statRes.get(i, "date_fin")).longValue();

            Date d = new Date(statMissionDebutTimestamp * 1000);
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            String statMissionDebutS = f.format(d);

            d = new Date(statMissionFinTimestamp * 1000);
            f = new SimpleDateFormat("dd/MM/yyyy");
            String statMissionFinS = f.format(d);

            int statMissionHours = statMissionWorkTime / 3600;

            String statMissionStyle = "style='text-align:center; font-family:Optima; font-size:11px'";
            String statMissionS = String.format("<html><div %s> %s <br> %s heures</div></html>", statMissionStyle,
                    statMission, statMissionHours);

            JLabel statMissionLabel = new JLabel(statMissionS, JLabel.CENTER);

            String titledBorderStyle = "style='font-weight:normal; font-style:italic; font-size:9px; font-family:Optima'";
            String topTitledBorderS = String.format("<html><span %s> %s </span></html>", titledBorderStyle,
                    statMissionDebutS);
            String bottomTitledBorderS = String.format("<html><span %s>  %s</span></html>", titledBorderStyle,
                    statMissionFinS);

            TitledBorder topTitledBorder = new TitledBorder(BorderFactory.createEmptyBorder(), topTitledBorderS);
            topTitledBorder.setTitleJustification(TitledBorder.CENTER);

            TitledBorder bottomTitledBorder = new TitledBorder(BorderFactory.createEmptyBorder(), bottomTitledBorderS);
            bottomTitledBorder.setTitleJustification(TitledBorder.CENTER);
            bottomTitledBorder.setTitlePosition(TitledBorder.BOTTOM);

            CompoundBorder titledBorder = new CompoundBorder(topTitledBorder, bottomTitledBorder);

            statMissionLabel.setBorder(titledBorder);

            if (!missionColors.containsKey(statMission)) {
                Random ran = new Random();
                missionColors.put(statMission,
                        new Color(ran.nextInt(200) + 56, ran.nextInt(200) + 56, ran.nextInt(200) + 56));
            }

            statMissionLabel.setBackground(missionColors.get(statMission));
            statMissionLabel.setOpaque(true);

            gbcColumn.gridx = 0;
            gbcColumn.gridy = i;
            gbcColumn.gridheight = 1;
            gbcColumn.gridwidth = 1;
            gbcColumn.weightx = 1;
            gbcColumn.weighty = (float) statMissionWorkTime / (float) myTotalWorkTime;
            gbcColumn.fill = GridBagConstraints.BOTH;
            gbcColumn.anchor = GridBagConstraints.CENTER;
            gbcColumn.insets = new Insets(8, 8, 8, 8);
            columnPanel.add(statMissionLabel, gbcColumn);
        }

        return columnPanel;
    }

    private static JPanel getStatsByMonthPanel(ResultArray participants) {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        Number dateDebutTimestampN = (Number) project.get(0, "date_debut");
        Number dateFinTimestampN = (Number) project.get(0, "date_fin");

        long dateDebutTimestamp = dateDebutTimestampN.longValue();
        long dateFinTimestamp;

        if (dateFinTimestampN == null) {
            dateFinTimestamp = Calendar.getInstance().getTimeInMillis() / 1000;
        } else {
            dateFinTimestamp = dateFinTimestampN.longValue();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateDebutTimestamp * 1000);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 1);
        ArrayList<Long> timestampAL = new ArrayList<>();
        timestampAL.add(calendar.getTimeInMillis() / 1000);

        while (timestampAL.get(timestampAL.size() - 1) < dateFinTimestamp) {
            int nextYear = calendar.get(Calendar.YEAR);
            if (calendar.get(Calendar.MONTH) == 11) {
                nextYear++;
            }
            int nextMonth = (calendar.get(Calendar.MONTH) + 1) % 12;
            calendar.set(nextYear, nextMonth, 1, 0, 0, 1);
            timestampAL.add(calendar.getTimeInMillis() / 1000);
        }

        for (int j = 0; j < timestampAL.size() - 1; j++) {
            calendar.setTimeInMillis(timestampAL.get(j) * 1000);
            String monthStyle = "style=''";
            String monthS = String.format("<html><span %s>%s %d</span></html>", monthStyle,
                    monthNames[calendar.get(Calendar.MONTH)], calendar.get(Calendar.YEAR));

            JLabel monthLabel = new JLabel(monthS);
            monthLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK),
                    BorderFactory.createEmptyBorder(7, 12, 7, 12)));

            gbc.gridx = 0;
            gbc.gridy = j + 1;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(monthLabel, gbc);
        }

        JLabel emptyLabel = new JLabel("");

        emptyLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(emptyLabel, gbc);

        for (int j = 0; j < participants.size; j++) {

            String nom = (String) participants.get(j, "nom");
            String prenom = (String) participants.get(j, "prenom");
            int participantID = (int) participants.get(j, "id");

            String headerStyle = "style='font-weight:bold; font-size:14px; font-family:Optima; color:#444444'";
            String headerS = String.format("<html><span %s> %s %s </span></html>", headerStyle, nom.toUpperCase(),
                    prenom);

            JLabel headerLabel = new JLabel(headerS, JLabel.CENTER);

            headerLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK),
                    BorderFactory.createEmptyBorder(7, 7, 7, 7)));

            gbc.gridx = j + 1;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(headerLabel, gbc);

            int nbMonth = timestampAL.size() - 1;

            for (int monthIndex = 0; monthIndex < nbMonth; monthIndex++) {
                long begMonth = timestampAL.get(monthIndex);
                long endMonth = timestampAL.get(monthIndex + 1) - 1;

                ResultArray myMonthStats = db.getMyMonthStatsGroupByMission(projectID, participantID, begMonth,
                        endMonth);

                int myTotalWorkTime = 0;
                for (int k = 0; k < myMonthStats.size; k++) {
                    myTotalWorkTime += (int) myMonthStats.get(k, "temps_travail_total");
                }

                JPanel monthCellPanel = new JColoredPanel();
                monthCellPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbcM = new GridBagConstraints();

                String monthHoursS;
                if (myTotalWorkTime / 3600 <= 1) {
                    monthHoursS = String.format("<html>%d heure</html>", myTotalWorkTime / 3600);
                } else {
                    monthHoursS = String.format("<html>%d heures</html>", myTotalWorkTime / 3600);
                }

                JLabel monthHoursLabel = new JLabel(monthHoursS, JLabel.CENTER);
                monthHoursLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK),
                        BorderFactory.createEmptyBorder()));

                gbcM.gridx = 0;
                gbcM.gridy = 0;
                gbcM.gridheight = GridBagConstraints.REMAINDER;
                gbcM.gridwidth = 1;
                gbcM.weightx = 1;
                gbcM.weighty = 1;
                gbcM.fill = GridBagConstraints.BOTH;
                gbcM.anchor = GridBagConstraints.CENTER;
                gbcM.insets = new Insets(0, 0, 0, 0);
                monthCellPanel.add(monthHoursLabel, gbcM);

                for (int missionIndex = 0; missionIndex < myMonthStats.size; missionIndex++) {

                    String missionValue = (String) myMonthStats.get(missionIndex, "mission");
                    Number missionWorkTime = (Number) myMonthStats.get(missionIndex, "temps_travail_total");

                    String missionS = String.format("<html><span>%s - %d heures", missionValue,
                            missionWorkTime.longValue() / 3600);
                    JLabel missionLabel = new JLabel(missionS);

                    gbcM.gridx = 1;
                    gbcM.gridy = missionIndex;
                    gbcM.gridheight = 1;
                    gbcM.gridwidth = 1;
                    gbcM.weightx = 1;
                    gbcM.weighty = 1;
                    gbcM.fill = GridBagConstraints.VERTICAL;
                    gbcM.anchor = GridBagConstraints.CENTER;
                    gbcM.insets = new Insets(0, 5, 0, 5);
                    monthCellPanel.add(missionLabel, gbcM);
                }

                if (myMonthStats.size == 0) {
                    monthCellPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                } else {
                    monthCellPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
                }

                gbc.gridx = j + 1;
                gbc.gridy = monthIndex + 1;
                gbc.gridheight = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1;
                gbc.weighty = 0;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(0, 0, 0, 0);
                panel.add(monthCellPanel, gbc);
            }
        }

        // DEBUT TOTAL ----------------------------------------

        String headerStyle = "style='font-weight:bold; font-size:14px; font-family:Optima; color:#444444'";
        String headerS = String.format("<html><span %s> TOTAL </span></html>", headerStyle);

        JLabel headerLabel = new JLabel(headerS, JLabel.RIGHT);

        headerLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK),
                BorderFactory.createEmptyBorder(7, 7, 7, 7)));

        gbc.gridx = participants.size + 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(headerLabel, gbc);

        int nbMonth = timestampAL.size() - 1;

        for (int monthIndex = 0; monthIndex < nbMonth; monthIndex++) {
            long begMonth = timestampAL.get(monthIndex);
            long endMonth = timestampAL.get(monthIndex + 1) - 1;

            ResultArray totalMonthStats = db.getTotalMonthStatsGroupByMission(projectID, begMonth, endMonth);

            int myTotalWorkTime = 0;
            for (int k = 0; k < totalMonthStats.size; k++) {
                myTotalWorkTime += (int) totalMonthStats.get(k, "temps_travail_total");
            }

            JPanel monthCellPanel = new JColoredPanel();
            monthCellPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbcM = new GridBagConstraints();

            String monthHoursS;
            if (myTotalWorkTime / 3600 <= 1) {
                monthHoursS = String.format("<html>%d heure</html>", myTotalWorkTime / 3600);
            } else {
                monthHoursS = String.format("<html>%d heures</html>", myTotalWorkTime / 3600);
            }

            JLabel monthHoursLabel = new JLabel(monthHoursS, JLabel.CENTER);
            monthHoursLabel.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK),
                    BorderFactory.createEmptyBorder()));

            gbcM.gridx = 0;
            gbcM.gridy = 0;
            gbcM.gridheight = GridBagConstraints.REMAINDER;
            gbcM.gridwidth = 1;
            gbcM.weightx = 1;
            gbcM.weighty = 1;
            gbcM.fill = GridBagConstraints.BOTH;
            gbcM.anchor = GridBagConstraints.CENTER;
            gbcM.insets = new Insets(0, 0, 0, 0);
            monthCellPanel.add(monthHoursLabel, gbcM);

            for (int missionIndex = 0; missionIndex < totalMonthStats.size; missionIndex++) {

                String missionValue = (String) totalMonthStats.get(missionIndex, "mission");
                Number missionWorkTime = (Number) totalMonthStats.get(missionIndex, "temps_travail_total");

                String missionS = String.format("<html><span>%s - %d heures", missionValue,
                        missionWorkTime.longValue() / 3600);
                JLabel missionLabel = new JLabel(missionS);

                gbcM.gridx = 1;
                gbcM.gridy = missionIndex;
                gbcM.gridheight = 1;
                gbcM.gridwidth = 1;
                gbcM.weightx = 1;
                gbcM.weighty = 1;
                gbcM.fill = GridBagConstraints.VERTICAL;
                gbcM.anchor = GridBagConstraints.CENTER;
                gbcM.insets = new Insets(0, 5, 0, 5);
                monthCellPanel.add(missionLabel, gbcM);
            }

            if (totalMonthStats.size == 0) {
                monthCellPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            } else {
                monthCellPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
            }

            gbc.gridx = participants.size + 1;
            gbc.gridy = monthIndex + 1;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add(monthCellPanel, gbc);
        }

        // FIN TOTAL -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        panel.add(new JLabel(""), gbc);

        return panel;
    }
}
