package com.aguedamaiara.api.MarkdownNote.controller;

import ch.qos.logback.core.util.FileUtil;
import com.aguedamaiara.api.MarkdownNote.dto.TextRequest;
import com.vladsch.flexmark.html.HtmlRenderer;
import org.apache.commons.io.FileUtils;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Portuguese;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class MarkdownController {
    private static final Logger logger = LoggerFactory.getLogger(MarkdownController.class);

    @PostMapping("/notes/check-grammar")
    public ResponseEntity<List<String>> checkGrammar(@RequestBody TextRequest request) {
        List<String> response;
        try {
            JLanguageTool languageTool = new JLanguageTool(new Portuguese());
            List<RuleMatch> matches = languageTool.check(request.text());
            response = matches.stream().map(match -> "Erro na posição " + match.getFromPos() + "-" + match.getToPos() + ": " + match.getMessage()).toList();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(e.getMessage()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/notes/save")
    public ResponseEntity<String> saveNote(@RequestBody TextRequest request) {
        String noteId = UUID.randomUUID().toString();
        File file = new File("notes/" + noteId + ".md");

        try {
            FileUtils.writeStringToFile(file, request.text(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Nota guardada com ID: " + noteId);
    }

    @GetMapping("/notes")
    public ResponseEntity<List<String>> listNotes() {
        File dir = new File("notes/");
        List<String> response = Arrays.stream(Objects.requireNonNull(dir.listFiles())).filter(File::isFile).map(File::getName).toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/notes/{noteId}")
    public ResponseEntity<String> getNoteAsHtml(@PathVariable String noteId) {
        File file = new File("notes/" + noteId + ".md");
        File htmlFile = new File("html_notes/");

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nota não encontrada");
        }
        try {
            String markdownText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            Node document = parser.parse(markdownText);
            String htmlContent = renderer.render(document);

            FileUtils.writeStringToFile(htmlFile, htmlContent, StandardCharsets.UTF_8);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ler a nota");
        }
    }
}