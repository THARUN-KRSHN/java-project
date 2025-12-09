package com.pollingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // path or URL to the symbol image
    private String symbolUrl;

    // category (e.g., dept, union)
    private String category;

    @Column(nullable = false)
    private Long voteCount = 0L;

    public Candidate() {}
    public Candidate(String name, String symbolUrl, String category) {
        this.name = name;
        this.symbolUrl = symbolUrl;
        this.category = category;
    }

    // getters/setters
    // ...
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getSymbolUrl(){return symbolUrl;}
    public void setSymbolUrl(String symbolUrl){this.symbolUrl=symbolUrl;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category=category;}
    public Long getVoteCount(){return voteCount;}
    public void setVoteCount(Long voteCount){this.voteCount=voteCount;}
}
