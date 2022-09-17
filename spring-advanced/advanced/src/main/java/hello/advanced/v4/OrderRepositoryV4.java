package hello.advanced.v4;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final LogTrace trace;

    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                // 저장 로직
                if( itemId.equals("ex")) {
                    throw new IllegalStateException("예외 발생!");
                }
                sleep(1000);

                return null;
            }
        };

        template.execute("OrderRepository.save()");
    }


    public void save(TraceId traceId, String itemId) {
        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepositoryV4.request()");

            // 저장 로직
            if( itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }

            sleep(1000);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
