package es.upm.grise.profundizacion;

import java.util.Objects;

public class LoanApprovalService {

    /**
     * Método X: contiene decisiones encadenadas y compuestas para análisis estructural.
     *
     * Regla (simplificada):
     * - Entradas inválidas -> excepción
     * - Score < 500 -> REJECTED
     * - 500..649 -> si income alto y no tiene impagos -> MANUAL_REVIEW; si no -> REJECTED
     * - >= 650 -> si amount <= income*8 -> APPROVED; si no -> MANUAL_REVIEW
     * - Además, si el cliente es VIP y score>=600 y no tiene impagos -> se eleva a APPROVED si estaba en MANUAL_REVIEW
     */
    public Decision evaluateLoan(
            Applicant applicant,
            int amountRequested,
            int termMonths
    ) { // 1
        validate(applicant, amountRequested, termMonths); // 2

        int score = applicant.creditScore(); // 3
        boolean hasDefaults = applicant.hasRecentDefaults(); // 4
        int income = applicant.monthlyIncome(); // 5

        Decision decision; // 6

        if (score < 500) { // 7
            decision = Decision.REJECTED; // 8
        } else if (score < 650) { // 9
            if (income >= 2500 && !hasDefaults) { // 10
                decision = Decision.MANUAL_REVIEW; // 11
            } else {
                decision = Decision.REJECTED; // 12
            }
        } else {
            if (amountRequested <= income * 8) { // 13
                decision = Decision.APPROVED; // 14
            } else {
                decision = Decision.MANUAL_REVIEW; //15
            }
        }

        if (decision == Decision.MANUAL_REVIEW
                && applicant.isVip()
                && score >= 600
                && !hasDefaults) { // 16
            decision = Decision.APPROVED; // 17
        }

        return decision; // 18
    } // 19

    private void validate(Applicant applicant, int amountRequested, int termMonths) {
        Objects.requireNonNull(applicant, "applicant cannot be null");
        if (amountRequested <= 0) {
            throw new IllegalArgumentException("amountRequested must be > 0");
        }
        if (termMonths < 6 || termMonths > 84) {
            throw new IllegalArgumentException("termMonths must be between 6 and 84");
        }
        if (applicant.monthlyIncome() <= 0) {
            throw new IllegalArgumentException("monthlyIncome must be > 0");
        }
        if (applicant.creditScore() < 0 || applicant.creditScore() > 850) {
            throw new IllegalArgumentException("creditScore must be between 0 and 850");
        }
    }

    public enum Decision {
        APPROVED, MANUAL_REVIEW, REJECTED
    }

    public record Applicant(int monthlyIncome, int creditScore, boolean hasRecentDefaults, boolean isVip) { }
}

