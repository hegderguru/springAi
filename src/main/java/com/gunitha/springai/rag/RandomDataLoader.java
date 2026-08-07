package com.gunitha.springai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomDataLoader {

    @Autowired
    private VectorStore vectorStore;

    //@PostConstruct
    public void init() {
        List<String> list = List.of("Shakti Scheme: Free state bus travel for all women domiciled in Karnataka.",
                "Gruha Lakshmi: Monthly financial benefit of ₹2,000 for female family heads.",
                "Gruha Jyothi: Free domestic electricity up to 200 units per month.",
                "Anna Bhagya: 10 kg of free food grains per person for BPL/Antyodaya households.",
                "Yuva Nidhi: Unemployment stipends for graduates (₹3,000/month) and diploma holders (₹1,500/month) for up to two years.",
                "Bhagyalakshmi Scheme: Financial deposit given to girl children born in BPL families to discourage female foeticide.",
                "Udyogini Scheme: Interest subsidies and loans up to ₹3 Lakhs for aspiring women entrepreneurs.",
                "Stree Shakti Programme: Financial assistance, savings habits training, and bank link incentives for rural women Self-Help Groups (SHGs).",
                "Santhwana Scheme: Legal aid, shelter, counseling, and monetary support for women victims of domestic abuse or harassment.",
                "Mathru Purna Scheme: Nutrient-rich hot cooked midday meals for pregnant and lactating mothers across rural Anganwadis.",
                "Prasoothi Araike Scheme: Cash incentive given to poor pregnant rural women to manage post-natal medical expenditures.",
                "Madilu Kit Scheme: Medical protection kits provided to low-income new mothers containing essential baby hygiene materials.",
                "Thayi Bhagya Scheme: Free institutional delivery infrastructure for BPL pregnant women in private hospitals.",
                "Thayi Bhagya Plus: Expanded emergency obstetric medical care allowances across remote talukas.",
                "Ksheera Bhagya: Free glass of fresh milk provided weekly to school and Anganwadi children to combat malnutrition.",
                "Nagu Magu Ambulance Service: Dedicated free transport vehicles to carry mothers and newborns safely back home from government hospitals.",
                "Dhanalakshmi Scheme: Conditional cash transfers targeting immunization and birth certificate registration metrics for girls.",
                "Karnataka State Swavalambi Sarathi: 50% subsidy (up to ₹3 Lakhs) for commercial vehicle purchases by marginalized youth/women to promote self-employment.",
                "Chetana Scheme: Vocational and business skill mentorship pipelines exclusively for vulnerable women and single mothers.",
                "Shramashakthi Special Women Scheme: Low-interest loans combined with back-ended subsidies for micro-level business ownership by minority women.",
                "Arivu Education Loan Scheme: Ultra-low-interest higher education loans tailored for professional courses for backward class students.",
                "Vidya Siri Scheme: Hostel maintenance allowances and pocket money stipends for students residing outside government facilities.",
                "Prabhuddha Overseas Scholarship: 100% financial tuition sponsorships for SC/ST students accepted into prestigious global universities.",
                "Vrutti Protsaha Loan Scheme: Capital credits for skill certification courses and technical workforce placements.",
                "Free Laptop Scheme for Students: High-configuration laptops awarded to merit-holding SC/ST/OBC students entering degree courses.",
                "Kariyakrama Protsaha Scheme: Financial encouragement and fellowships awarded to research scholars working on state-centric challenges.",
                "CM Vidyanidhi Scheme: Scholarship payouts directly sent to children of farmers, weavers, and taxi/auto drivers.",
                "Devaraj Urs Pratibha Puraskar: Merit rewards honoring state board topper achievements from backward classes.",
                "Swami Vivekananda Yuva Shakti Yojana: Capital and operational grants for youth-led self-help associations.",
                "Yuva Spandana Program: Comprehensive youth mental health, counseling, and psychological response help desks across districts.",
                "Vrutti Darshi Program: Industry exposures and operational site visits mapped out for students in government colleges.",
                "Post-Matric Fee Concession: Full institutional fee waivers across degree streams for low-income candidates.",
                "Pre-Matric Scholarship Scheme: Annual textbook and uniform allowances keeping school dropout numbers low.",
                "Kittur Rani Channamma Puraskar: Monetary accolades celebrating exceptionally brave acts or academic milestones achieved by rural girls.",
                "Sir M. Visvesvaraya Scholarship: Financial awards assisting engineering aspirants from economically backward backgrounds.",
                "Krushi Bhagya: Grants for rainwater farm ponds, diesel pumps, and micro-irrigation systems in dry zones.",
                "Ganga Kalyana Scheme: Free open well drilling and exploratory borewell operations for marginalized farm plots.",
                "Raitha Vidya Nidhi: Dedicated scholarship assistance for children of marginal agricultural workers.",
                "Krushi Aranya Protsaha Yojane: Financial incentives paying farmers fixed yearly amounts for every tree sapling grown successfully on private land.",
                "Bhoochetana Scheme: Advanced soil health testing kits and balanced micronutrient allocation systems.",
                "Raitha Siri Scheme: Direct financial transfers pushing for the adoption and cultivation of climate-resilient millets.",
                "Ksheera Abhivruddi Yojana: Direct financial milk-pouring incentives for dairy farmers supplying state cooperatives.",
                "Karnataka Sandalwood Policy: Complete relaxation of rules on private sandalwood farming and tree felling to enrich growers.",
                "Matsya Ashraya Yojane: Financial house-building assistance ensuring housing safety for coastal and inland traditional fishermen.",
                "Pashu Sanjeevini Scheme: Mobile veterinary hospital networks providing emergency diagnostic care directly to livestock sheds.",
                "Sujala Watershed Development: Integrated natural resource rejuvenation focusing on structural water conservation in drought zones.",
                "Mukhyamantri Raitha Vidruna Parihara: Targeted farm loan waivers and structural relief during catastrophic crop yield collapses.",
                "Krishi Yanthrikarana Scheme: Heavy machinery rental discounts and capital purchase subsidies for high-cost tractors.",
                "Savayava Krishi Yojane: Structural conversion grants supporting certified organic chemical-free crop farming.",
                "Karnataka Agro-Processing Policy Incentives: Capital setups enabling cold storage construction and processing hubs.",
                "Sandhya Suraksha Yojana: Monthly survival pension support of ₹1,200 alongside concessional transport for low-income seniors.",
                "Ayushman Bharat-Arogya Karnataka: Joint integrated system extending cashless secondary and tertiary medical treatments to millions.",
                "Manaswini Scheme: Monthly financial safety payout targeted at divorced or unmarried poor rural women.",
                "Mythri Scheme: Specialized financial monthly maintenance pension system for the transgender community.",
                "Indira Gandhi National Old Age Pension: State co-shared regular social security payment protecting vulnerable senior citizens.",
                "Arogya Kavacha (108 Emergency): Round-the-clock integrated emergency ambulance networks managing hospital transits.",
                "Namma Clinic: Suburban community urban clinics offering diagnosis, essential labs, and free medicines.",
                "Vajpayee Arogya Sree Scheme: Special critical surgery cost protection for BPL families facing tertiary health crises.",
                "Janani Suraksha Yojana: Central-state partnered cash rewards boosting Institutional medical birth deliveries.",
                "Free Dialysis Scheme: Complimentary kidney dialysis services accessible at all taluka and district state hospitals.",
                "Endosulfan Victims Pension: Lifetime medical compensation pensions for families affected by legacy pesticide poisoning.",
                "Senior Citizen Day Care Center Scheme: Urban daytime recreational centers providing nutrition and checks to elders.",
                "Mathru Vandana Scheme: Partial wage-loss recovery direct cash benefits helping new mothers recuperate.",
                "Blindness Control Spectacles Scheme: Completely free eye surgeries and free customized spectacles given to needy elder blocks.",
                "Hearing Aid Distribution Scheme: Free digital auditory assist gear distributed to hearing-challenged citizens.",
                "4% Interest Subsidy Scheme for SC/ST: Business loans where interest overhead beyond 4% is paid by the state.",
                "Chaitanya Scheme: Loans coupled with capital grants for setting up small business initiatives.",
                "Dr. B.R. Ambedkar ISB Scheme: Targeted industry, service, and business credit options ensuring livelihood expansions.",
                "Airavatha Scheme: Commercial car booking platform aggregates offering corporate cab-driving tie-ups to youth.",
                "Samrudhi Scheme: Industry franchise setup sponsorships pairing youth with established consumer brands.",
                "ST Marriage Assistance Scheme: Monetary grant assisting backward tribe families with wedding expenditures.",
                "SC Marriage Assistance Scheme: Explicit wedding support transfers protecting backward caste households.",
                "SC Widow Re-Marriage Assistance Scheme: Cash reward financial encouragement promoting rehabilitation of young widows.",
                "ST Widow Re-Marriage Assistance Scheme: Identical targeted rehabilitation incentives mapped to tribal women.",
                "SC Inter-Caste Couple Assistance: Significant financial rewards given to couples opting for inter-caste marriages to break social barriers.",
                "SC Intra-Caste Marriage Support: Institutional marriage financial aid helping low-income interior communities.",
                "ST Inter-Caste Marriage Support: Explicit fiscal aid honoring social integrity via tribal cross-marriages.",
                "ST Intra-Caste Marriage Support: Financial event grants supporting traditional local tribal couples.",
                "Babu Jagjivan Ram Self Employment: Micro-enterprise support tools given directly to artisanal leather worker families.",
                "Valmiki Development Corporation Loans: Livelihood upgrade finances strictly serving Scheduled Tribe populations.",
                "Shrama Shakthi Loan Scheme: Skilled minority artisans get ₹50,000 credit setups at just 4% interest to start workshops.",
                "Shaadi Mahal Scheme: Capital financial aid for building community convention centers across minority pockets.");

        List<Document> documentList = list.stream().map(Document::new).toList();
        vectorStore.add(documentList);
    }

}
